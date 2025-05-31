package com.sici.chat.config.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.config.thread
 * @author: 20148
 * @description: 线程异常全局处理器
 * @create-date: 12/10/2024 2:50 PM
 * @version: 1.0
 */

@Slf4j
public class GlobalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Thread {} encountered an uncaught exception: {}", t.getName(), e.getMessage(), e);
    }
}
