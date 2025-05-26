package com.sici.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author jackey
 * @description 线程测试
 * @date 5/26/25 15:47
 */
public class ThreadTest {
    public static void main(String[] args) {
        List<Future> futures1 = new ArrayList<>();
        futures1.add(CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3 * 1000);
                System.out.println("futures1-任务1执行完毕");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        futures1.add(CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3 * 1000);
                System.out.println("futures1-任务2执行完毕");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));

        CompletableFuture<Void> ff1 = CompletableFuture.allOf(futures1.toArray(new CompletableFuture[0]));

        List<Future> futures2 = new ArrayList<>();
        futures2.add(CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5 * 1000);
                System.out.println("futures2-任务1执行完毕");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        futures2.add(CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5 * 1000);
                System.out.println("futures2-任务2执行完毕");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));

        CompletableFuture<Void> ff2 = CompletableFuture.allOf(futures2.toArray(new CompletableFuture[0]));

        CompletableFuture.allOf(ff1, ff2).join();
    }
}
