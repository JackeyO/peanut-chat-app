package com.sici.chat.canal.sync;

import com.sici.chat.config.thread.ThreadPoolConfiguration;
import com.sici.chat.model.canal.event.DataChangeEvent;
import com.sici.chat.util.CompletableFutureUtils;
import com.sici.common.constant.future.task.FutureTaskConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author jackey
 * @description
 * @date 5/29/25 17:48
 */
@Component
@Slf4j
public class DataChangeHandlerFactory {
    @Resource
    @Qualifier(ThreadPoolConfiguration.CHAT_CANAL_SYNC)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static Map<String, List<DataChangeHandler>> dataChangeHandlerMap = new HashMap<>();

    private DataChangeHandlerFactory() {

    }

    public static void registerDataChangeHandler(DataChangeHandler dataChangeHandler) {
        if (Objects.nonNull(dataChangeHandler)) {
            dataChangeHandlerMap.computeIfAbsent(dataChangeHandler.getDatabase() + "." + dataChangeHandler.getTable(),
                    (key) -> new ArrayList<>()).add(dataChangeHandler);
        }
    }

    public void handleDataChangeEvent(DataChangeEvent dataChangeEvent) {
        String database = dataChangeEvent.getDatabase();
        String table = dataChangeEvent.getTable();
        List<DataChangeHandler> dataChangeHandlers = Optional.ofNullable(dataChangeHandlerMap.get(database + "." + table))
                .orElse(Lists.newArrayList());

        if (CollectionUtils.isEmpty(dataChangeHandlers))  {
            log.warn("no data-change-handler found for database:{}, table:{}", database, table);
            return ;
        }

        // 异步执行
        CompletableFutureUtils.safeRunAsync(database + "." + table + FutureTaskConstant.DATA_SYNC_FUTURE_TASK, () -> dataChangeHandlers
                        .forEach(dataChangeHandler ->
                                CompletableFutureUtils.safeRunAsync(database + "." + table + dataChangeHandler.getSyncType() + FutureTaskConstant.DATA_SYNC_FUTURE_TASK,
                                        () -> dataChangeHandler.handleDataChangeEvent(dataChangeEvent), threadPoolTaskExecutor)),
                threadPoolTaskExecutor);
    }
}
