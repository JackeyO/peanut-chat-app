package com.sici.chat.thread;

import com.sici.chat.config.thread.ThreadPoolConfiguration;
import com.sici.chat.util.CompletableFutureUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author jackey
 * @description
 * @date 5/31/25 13:30
 */
@SpringBootTest
@Slf4j
public class TheadPoolTest {
    @Resource
    @Qualifier(ThreadPoolConfiguration.CHAT_PUBLIC_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Test
    void testThreadPool() {
//        CompletableFuture.runAsync(() -> {
//            int i = 1 / 0;
//        }, threadPoolTaskExecutor);
//        threadPoolTaskExecutor.execute(() -> {
//            int i = 1 / 0;
//        });

        CompletableFutureUtils.safeRunAsync("测试任务", () -> {
            log.info("测试任务开始执行");
            int i = 1 / 0; // 故意抛出异常
            log.info("测试任务执行完成");
        }, threadPoolTaskExecutor);
    }
}
