package com.sici.chat.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.config.thread
 * @author: 20148
 * @description: 线程池配置
 * @create-date: 12/10/2024 2:39 PM
 * @version: 1.0
 */

@Configuration
@EnableAsync
// TODO: 扩展线程池，使其支持统计任务执行耗时时间并记录日志  || created by 20148 at 12/10/2024 5:24 PM
public class ThreadPoolConfiguration implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        return AsyncConfigurer.super.getAsyncExecutor();
    }

    public static final String CHAT_PUBLIC_EXECUTOR = "publicExecutor";
    public static final String CHAT_WS_EXECUTOR = "wsExecutor";

    @Bean(CHAT_PUBLIC_EXECUTOR)
    @Primary
    public ThreadPoolTaskExecutor chatPublicExecutor() {
        ThreadPoolTaskExecutor tpe = new ThreadPoolTaskExecutor();
        tpe.setCorePoolSize(10);
        tpe.setMaxPoolSize(10);
        tpe.setQueueCapacity(200);
        tpe.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        tpe.setThreadFactory(new MyThreadFactory(tpe));
        tpe.setThreadNamePrefix(CHAT_PUBLIC_EXECUTOR);
        return tpe;
    }

    @Bean(CHAT_WS_EXECUTOR)
    public ThreadPoolTaskExecutor chatWsExecutor() {
        ThreadPoolTaskExecutor tpe = new ThreadPoolTaskExecutor();
        tpe.setCorePoolSize(16);
        tpe.setMaxPoolSize(16);
        tpe.setQueueCapacity(1000);
        tpe.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        tpe.setThreadFactory(new MyThreadFactory(tpe));
        tpe.setThreadNamePrefix(CHAT_PUBLIC_EXECUTOR);
        return tpe;
    }
}
