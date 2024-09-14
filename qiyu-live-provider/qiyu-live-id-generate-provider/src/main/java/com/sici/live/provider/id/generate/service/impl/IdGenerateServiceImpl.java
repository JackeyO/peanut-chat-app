package com.sici.live.provider.id.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.common.constant.id.generate.IdGenerateServiceConstant;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.enums.id.generate.IdGenerateEnums;
import com.sici.common.result.ResponseResult;
import com.sici.live.model.id.generate.pojo.IdGeneratePO;
import com.sici.live.provider.id.generate.bo.LocalIdBo;
import com.sici.live.provider.id.generate.bo.LocalSeqIdBo;
import com.sici.live.provider.id.generate.bo.LocalUnSeqIdBo;
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
    private static Map<Integer, LocalIdBo> localIdBoMap = new ConcurrentHashMap<>();
    private static Map<Integer, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16, 3000, TimeUnit.SECONDS,
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
            semaphoreMap.put(idGeneratePO.getId(), new Semaphore(1, true));
            putToLocalIdMap(idGeneratePO);
            refreshDbSeqIdSegment(localIdBoMap.get(idGeneratePO.getId()));
            localIdBoMap.get(idGeneratePO.getId()).setPushed(false);
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
        LocalIdBo localIdBo = null;
        if (id == null || (localIdBo = localIdBoMap.get(id)) == null || !(localIdBo instanceof LocalSeqIdBo)) {
            log.error("分布式id生成器业务==>参数无效:id");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        LocalSeqIdBo localSeqIdBo = (LocalSeqIdBo) localIdBo;

        long nextId;
        try {
            nextId = localSeqIdBo.getNextId();
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ID_SEGMENT_IS_FULL);
        }

        checkAndRefreshDbSeqIdSegment(localSeqIdBo);
        checkAndRefreshLocalSeqIdSegment(localSeqIdBo);

        return ResponseResult.okResult(nextId);
    }

    /**
     * 获取无序id
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult<Long> getNoSeqId(Integer id) {
        LocalIdBo localIdBo = null;
        if (id == null || (localIdBo = localIdBoMap.get(id)) == null || !(localIdBo instanceof LocalUnSeqIdBo)) {
            log.error("分布式id生成器业务==>参数无效:id");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        LocalUnSeqIdBo localUnSeqIdBo = (LocalUnSeqIdBo) localIdBo;
        long nextId;
        try {
            nextId = localUnSeqIdBo.getNextId();
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ID_SEGMENT_IS_FULL);
        }

        checkAndRefreshDbSeqIdSegment(localUnSeqIdBo);
        checkAndRefreshLocalSeqIdSegment(localUnSeqIdBo);

        return ResponseResult.okResult(nextId);
    }

    /**
     * 在本地id段达到更新阈值后，更新数据库的id段信息
     *
     * @param idGeneratePO
     */

    private boolean updateDbIdSegment(IdGeneratePO idGeneratePO) {
        boolean updated = update(new LambdaUpdateWrapper<IdGeneratePO>()
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
    private boolean tryUpdateDbIdSegment(IdGeneratePO idGeneratePO) {
        boolean success = updateDbIdSegment(idGeneratePO);
        for (int i = 0; i < 3 && !success; i++) {
            success = updateDbIdSegment(idGeneratePO);
        }
        if (!success) throw new RuntimeException("数据库id段更新失败，竞争过于激烈，id:" + idGeneratePO.getId());
        return success;
    }

    /**
     * 在达到更新阈值rate后，更新数据库id段信息
     *
     * @param localIdBo
     */
    public void refreshDbSeqIdSegment(LocalIdBo localIdBo) {
        // 控制最多只有一个线程去执行数据库id段的更新操作,避免重复更新
        Semaphore semaphore = semaphoreMap.get(localIdBo.getId());
        if (!localIdBo.hasPushed()) {
            boolean acquired = semaphore.tryAcquire();
            if (acquired) {
//                 异步更新数据库id段信息
//                threadPoolExecutor.execute(() -> {
                try {
                    log.info("id-generate-service==>开始进行数据库id段更新操作");
                    IdGeneratePO idGeneratePO = getById(localIdBo.getId());
                    tryUpdateDbIdSegment(idGeneratePO);
                    localIdBoMap.get(localIdBo.getId()).setPushed(true);
                    log.info("id-generate-service==>数据库id段更新完成");
                } catch (Exception e) {
                    log.info("id-generate-server==>数据库id段更新失败");
                } finally {
                    semaphore.release();
                }
//                });
            }
        }
    }

    /**
     * 检查本地id段是否达到阈值，到达后向数据库推送
     *
     * @param localIdBo
     */
    private void checkAndRefreshDbSeqIdSegment(LocalIdBo localIdBo) {
        if (!localIdBo.hasPushed() && localIdBo.currentIdSegOverThreshHold()) {
            refreshDbSeqIdSegment(localIdBo);
        }
    }

    /**
     * 将数据库查到的id段信息放到本地id段中，并存入本地id段Map集合
     *
     * @param idGeneratePO
     */
    private void putToLocalIdMap(IdGeneratePO idGeneratePO) {
        LocalIdBo localIdBo = null;
        if (idGeneratePO.getIsSeq() == IdGenerateEnums.SEQ_ID.getCode()) {
            LocalSeqIdBo localSeqIdBo = new LocalSeqIdBo();
            localSeqIdBo.setCurrentNum(new AtomicLong(idGeneratePO.getCurrentStart()));
            localIdBo = localSeqIdBo;
        } else {
            LocalUnSeqIdBo localUnSeqIdBo = new LocalUnSeqIdBo();
            localUnSeqIdBo.load(idGeneratePO.getCurrentStart(), idGeneratePO.getNextThreshold());
            localIdBo = localUnSeqIdBo;
        }
        localIdBo.setCurrentStart(idGeneratePO.getCurrentStart());
        localIdBo.setNextThreshHold(idGeneratePO.getNextThreshold());
        localIdBo.setStep(idGeneratePO.getStep());
        localIdBo.setId(idGeneratePO.getId());
        localIdBo.setPushed(false);
        localIdBoMap.put(localIdBo.getId(), localIdBo);
    }

    private void putToLocalIdMap(LocalIdBo localIdBo) {
        localIdBoMap.put(localIdBo.getId(), localIdBo);
    }

    private void refreshLocalSeqIdSegment(LocalIdBo localIdBo) {
        // 控制最多只有一个线程去执行本地id段的更新操作,避免重复更新
        Semaphore semaphore = semaphoreMap.get(localIdBo.getId());
        boolean acquired = semaphore.tryAcquire();
        if (acquired) {
            try {
                localIdBo.refreshLocalIdSeg();
                putToLocalIdMap(localIdBo);
                log.info("id-generate-service==>本地id段刷新成功");
            } catch (Exception e) {
                log.info("id-generate-service==>本地id段刷新失败");
            } finally {
                semaphore.release();
            }
        }
    }

    private void checkAndRefreshLocalSeqIdSegment(LocalIdBo localIdBo) {
        // 如果本地id段已经满了，刷新本地段
        if (localIdBo.currentIdSegIsFull()) {
            refreshLocalSeqIdSegment(localIdBo);
        }
    }
}