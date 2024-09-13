package com.sici.live.provider.id.generate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.common.constant.id.generate.IdGenerateServiceConstant;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.result.ResponseResult;
import com.sici.live.model.id.generate.pojo.IdGeneratePO;
import com.sici.live.provider.id.generate.bo.LocalSeqIdBo;
import com.sici.live.provider.id.generate.mapper.IdGenerateMapper;
import com.sici.live.provider.id.generate.service.IdGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.id.generate.service.impl
 * @author: 20148
 * @description: 分布式id生成
 * @create-date: 9/13/2024 3:26 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class IdGenerateServiceImpl extends ServiceImpl<IdGenerateMapper, IdGeneratePO>
        implements IdGenerateService, InitializingBean {
    private static Map<Integer, LocalSeqIdBo> localSeqIdBoMap = new ConcurrentHashMap<>();
    private double UPDATE_THRESH_RATE = 0.75;

    private static Map<Integer, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16, 3, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000),
            r -> {
                Thread thread = new Thread(r);
                thread.setName(IdGenerateServiceConstant.REFRESH_LOCAL_SEQ_ASYNC_THREAD_NAME + "-" + ThreadLocalRandom.current().nextInt(1000));
                return thread;
            });

    /**
     * bean初始化后会回调该方法
     */
    @Override
    public void afterPropertiesSet() {
        List<IdGeneratePO> idGeneratePOS = list();

        for (IdGeneratePO idGeneratePO : idGeneratePOS) {
            putToLocalSeqIdBoMap(idGeneratePO);
            semaphoreMap.put(idGeneratePO.getId(), new Semaphore(1, true));
        }
    }

    /**
     * 获取有序id
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult<Long> getSeqId(Integer id) {
        LocalSeqIdBo localSeqIdBo = null;
        if (id == null || (localSeqIdBo = localSeqIdBoMap.get(id)) == null) {
            log.error("分布式id生成器业务==>参数无效:id");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (localSeqIdBo.getCurrentNum().get() >= localSeqIdBo.getNextThreshHold()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ID_SEGMENT_IS_FULL);
        }
        long val = localSeqIdBo.getCurrentNum().getAndIncrement();

        checkAndRefreshDbSeqIdSegment(localSeqIdBo);
        checkAndRefreshLocalSeqIdSegment(localSeqIdBo);

        return ResponseResult.okResult(val);
    }

    /**
     * 获取无序id
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult<Long> getNoSeqId(Integer id) {
        return null;
    }

    /**
     * 判断当前id段是否已经达到阈值
     *
     * @param localSeqIdBo
     * @return
     */
    private boolean checkLocalIdSegment(LocalSeqIdBo localSeqIdBo) {
        long step = localSeqIdBo.getNextThreshHold() - localSeqIdBo.getCurrentStart();
        return localSeqIdBo.getCurrentNum().get() - localSeqIdBo.getCurrentStart() >= UPDATE_THRESH_RATE * step;
    }

    /**
     * 在id段达到更新阈值后，更新数据库的id段信息
     * @param idGeneratePO
     */

    private boolean updateIdSegment(IdGeneratePO idGeneratePO) {
        boolean updated = update(Wrappers.<IdGeneratePO>lambdaUpdate()
                .eq(IdGeneratePO::getId, idGeneratePO.getId())
                .eq(IdGeneratePO::getVersion, idGeneratePO.getVersion())
                .set(IdGeneratePO::getCurrentStart, idGeneratePO.getCurrentStart() + idGeneratePO.getStep())
                .set(IdGeneratePO::getNextThreshold, idGeneratePO.getNextThreshold() + idGeneratePO.getStep())
                .set(IdGeneratePO::getVersion, idGeneratePO.getVersion() + 1));
        return updated;
    }

    /**
     * 更新数据库id段信息
     *
     * @param idGeneratePO
     * @return
     */
    private boolean tryUpdateIdSegment(IdGeneratePO idGeneratePO) {
        boolean success = updateIdSegment(idGeneratePO);
        for (int i = 0; i < 3 && !success; i++) {
            idGeneratePO = getById(idGeneratePO.getId());
            success = updateIdSegment(idGeneratePO);
        }
        if (!success) throw new RuntimeException("id段抢占失败，竞争过于激烈，id:" + idGeneratePO.getId());
        return success;
    }

    private void putToLocalSeqIdBoMap(IdGeneratePO idGeneratePO) {
        localSeqIdBoMap.put(idGeneratePO.getId(), LocalSeqIdBo.builder()
                .id(idGeneratePO.getId())
                .currentStart(idGeneratePO.getCurrentStart())
                .nextThreshHold(idGeneratePO.getNextThreshold())
                .currentNum(new AtomicLong(idGeneratePO.getCurrentStart()))
                .build());
    }
    private void checkAndRefreshLocalSeqIdSegment(LocalSeqIdBo localSeqIdBo) {
        // 如果本地id段已经达到阈值，从数据库获取最新的id段
        if (localSeqIdBo.getCurrentNum().get() == localSeqIdBo.getNextThreshHold()) {
            IdGeneratePO idGeneratePO = getById(localSeqIdBo.getId());
            putToLocalSeqIdBoMap(idGeneratePO);
        }
    }

    /**
     * 在达到更新阈值rate后，更新数据库id段信息
     * @param localSeqIdBo
     */
    public void refreshDbSeqIdSegment(LocalSeqIdBo localSeqIdBo) {
        // 控制最多只有一个线程去执行数据库id段的更新操作,避免重复更新
        Semaphore semaphore = semaphoreMap.get(localSeqIdBo.getId());
        boolean acquired = semaphore.tryAcquire();
        if (acquired) {
            // 异步更新数据库段信息
            threadPoolExecutor.execute(() -> {
                log.info("id-generate-service==>开始进行数据库id段更新操作");
                IdGeneratePO idGeneratePO = getById(localSeqIdBo.getId());
                tryUpdateIdSegment(idGeneratePO);
                log.info("id-generate-service==>数据库id段更新完成");
                semaphore.release();
            });
        }
    }

    private void checkAndRefreshDbSeqIdSegment(LocalSeqIdBo localSeqIdBo) {
        if (checkLocalIdSegment(localSeqIdBo)) {
            refreshDbSeqIdSegment(localSeqIdBo);
        }
    }
}
