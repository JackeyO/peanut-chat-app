package com.sici.chat.config.thread;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.ThreadFactory;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.config.thread
 * @author: 20148
 * @description: 线程工厂
 * @create-date: 12/10/2024 2:44 PM
 * @version: 1.0
 */

public class MyThreadFactory implements ThreadFactory{
    private final ThreadFactory factory;
    public MyThreadFactory(ThreadFactory factory) {
        this.factory = factory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = factory.newThread(r);
        thread.setUncaughtExceptionHandler(new GlobalUncaughtExceptionHandler());
        return thread;
    }
}
