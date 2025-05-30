package com.sici.chat.task.es;

import com.sici.chat.es.sync.IndexSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author jackey
 * @description
 * @date 5/30/25 11:17
 */
@Slf4j
public abstract class AbstractIndexSyncTask implements IndexSyncTask, CommandLineRunner {
    public abstract IndexSyncService getIndexSyncService();
    private volatile Date lastIncrementalSyncTime;
    public abstract String getIndexSyncTaskName();

    @Override
    public void fullSync() {
        IndexSyncService indexSyncService = getIndexSyncService();
        CompletableFuture<Boolean> future = indexSyncService.fullSync();
        future.whenComplete((result, error) -> {
            if (Objects.nonNull(error)) {
                log.error("全量索引同步任务[{}]执行失败", getIndexSyncTaskName(), error);
            } else {
                log.info("全量索引同步任务[{}]执行成功", getIndexSyncTaskName());
                // 更新最后一次增量同步时间
                lastIncrementalSyncTime = new Date();
            }
        });
    }

    @Override
    public void incrementSync() {
        if (Objects.isNull(lastIncrementalSyncTime)) {
            log.error("未执行全量索引同步，无法执行增量同步，任务名称[{}]", getIndexSyncTaskName());
        }
        Date currentSyncTime = new Date();
        IndexSyncService indexSyncService = getIndexSyncService();
        CompletableFuture<Boolean> future = indexSyncService.incrementSync(lastIncrementalSyncTime);

        future.whenComplete((result, error) -> {
            if (Objects.isNull(result) || Boolean.FALSE.equals(result)) {
                log.error("增量索引同步任务[{}]执行失败", getIndexSyncTaskName(), error);
            } else {
                log.info("增量索引同步任务[{}]执行成功", getIndexSyncTaskName());
                // 更新最后一次增量同步时间
                lastIncrementalSyncTime = currentSyncTime;
            }
        });
    }

    /**
     * 执行全量同步任务
     * 在应用启动时自动执行一次
     */
    @Override
    public void run(String... args) {
        fullSync();
    }
}
