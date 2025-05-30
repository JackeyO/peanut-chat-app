package com.sici.chat.canal.sync;

import com.sici.chat.config.thread.ThreadPoolConfiguration;
import com.sici.chat.model.canal.event.DataChangeEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author jackey
 * @description
 * @date 5/29/25 17:48
 */
@Slf4j
public class DataChangeHandlerFactory {
    @Resource
    @Qualifier(ThreadPoolConfiguration.CHAT_CANAL_SYNC)
    private static ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static Map<String, List<DataChangeHandler>> dataChangeHandlerMap = new HashMap<>();

    private DataChangeHandlerFactory() {

    }

    public static void registerDataChangeHandler(DataChangeHandler dataChangeHandler) {
        if (Objects.nonNull(dataChangeHandler)) {
            dataChangeHandlerMap.computeIfAbsent(dataChangeHandler.getDatabase() + "." + dataChangeHandler.getTable(),
                    (key) -> new ArrayList<>()).add(dataChangeHandler);
        }
    }

    public static void handleDataChangeEvent(DataChangeEvent dataChangeEvent) {
        List<DataChangeHandler> dataChangeHandlers = Optional.ofNullable(dataChangeHandlerMap.get(dataChangeEvent.getDatabase() + "." + dataChangeEvent.getTable()))
                .orElse(Lists.newArrayList());

        if (CollectionUtils.isEmpty(dataChangeHandlers))  {
            log.warn("no data-change-handler found for database:{}, table:{}", dataChangeEvent.getDatabase(), dataChangeEvent.getTable());
            return ;
        }

        // 异步执行
        CompletableFuture.runAsync(() -> dataChangeHandlers.stream()
                        .forEach(dataChangeHandler ->
                                CompletableFuture.runAsync(() -> dataChangeHandler.handleDataChangeEvent(dataChangeEvent), threadPoolTaskExecutor)),
                threadPoolTaskExecutor);
    }
}
