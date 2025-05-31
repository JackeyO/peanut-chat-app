package com.sici.chat.util;

import com.sici.chat.context.RequestInfo;
import com.sici.chat.thread.task.TaskContext;
import com.sici.chat.thread.task.TaskContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * CompletableFuture增强工具类
 * 提供统一的异常处理机制和任务上下文传递
 */
@Slf4j
public class CompletableFutureUtils {
    
    /**
     * 增强的异常处理器，包含任务上下文信息
     */
    private static final Consumer<Throwable> ENHANCED_EXCEPTION_HANDLER = throwable -> {
        String taskInfo = TaskContextHolder.getCurrentTaskInfo();
        log.error("异步任务执行异常 {}: ", taskInfo, throwable);
        
        // 可以在这里添加其他处理逻辑，如发送警报、记录监控等
        TaskContext context = TaskContextHolder.getTaskContext();
        if (context != null && !context.getExtInfo().isEmpty()) {
            log.error("任务扩展信息: {}", context.getExtInfo());
        }
    };

    /**
     * 安全执行runAsync，带任务上下文和异常处理
     */
    public static CompletableFuture<Void> safeRunAsync(String taskName, Runnable runnable, Executor executor) {
        return safeRunAsync(taskName, null, runnable, executor);
    }
    
    /**
     * 安全执行runAsync，带任务ID、任务上下文和异常处理
     */
    public static CompletableFuture<Void> safeRunAsync(String taskName, String taskId, Runnable runnable, Executor executor) {
        return safeRunAsync(taskName, taskId, null, runnable, executor);
    }
    
    /**
     * 安全执行runAsync，带完整任务上下文和异常处理
     */
    public static CompletableFuture<Void> safeRunAsync(String taskName, String taskId, String taskDescription, Runnable runnable, Executor executor) {
        return safeRunAsync(taskName, taskId, taskDescription, runnable, executor, ENHANCED_EXCEPTION_HANDLER);
    }

    /**
     * 安全执行runAsync，带自定义异常处理
     */
    public static CompletableFuture<Void> safeRunAsync(String taskName, String taskId, String taskDescription, 
                                                      Runnable runnable, Executor executor, Consumer<Throwable> exceptionHandler) {
        // 捕获父线程的上下文
        TaskContext parentContext = new TaskContext(taskName, taskId, taskDescription);
        RequestInfo parentRequestInfo = RequestHolder.get();
        
        return CompletableFuture.runAsync(() -> {
            // 设置任务上下文
            TaskContextHolder.setTaskContext(parentContext);
//            TaskContextHolder.copyRequestInfoFromParent(parentRequestInfo);
            
            try {
                runnable.run();
            } catch (Exception e) {
                // 捕获并处理异常
                exceptionHandler.accept(e);
                throw e;
            } finally {
                // 清理上下文
                TaskContextHolder.clearTaskContext();
//                RequestHolder.remove();
            }
        }, executor);
    }

    /**
     * 安全执行supplyAsync，带任务上下文和异常处理
     */
    public static <T> CompletableFuture<T> safeSupplyAsync(String taskName, Supplier<T> supplier, Executor executor) {
        return safeSupplyAsync(taskName, null, supplier, executor);
    }
    
    /**
     * 安全执行supplyAsync，带任务ID、任务上下文和异常处理
     */
    public static <T> CompletableFuture<T> safeSupplyAsync(String taskName, String taskId, Supplier<T> supplier, Executor executor) {
        return safeSupplyAsync(taskName, taskId, null, supplier, executor);
    }
    
    /**
     * 安全执行supplyAsync，带完整任务上下文和异常处理
     */
    public static <T> CompletableFuture<T> safeSupplyAsync(String taskName, String taskId, String taskDescription, 
                                                          Supplier<T> supplier, Executor executor) {
        return safeSupplyAsync(taskName, taskId, taskDescription, supplier, executor, ENHANCED_EXCEPTION_HANDLER);
    }

    /**
     * 安全执行supplyAsync，带自定义异常处理
     */
    public static <T> CompletableFuture<T> safeSupplyAsync(String taskName, String taskId, String taskDescription,
                                                          Supplier<T> supplier, Executor executor, Consumer<Throwable> exceptionHandler) {
        // 捕获父线程的上下文
        TaskContext parentContext = new TaskContext(taskName, taskId, taskDescription);
        RequestInfo parentRequestInfo = RequestHolder.get();
        
        return CompletableFuture.supplyAsync(() -> {
//             设置任务上下文
            TaskContextHolder.setTaskContext(parentContext);
//            TaskContextHolder.copyRequestInfoFromParent(parentRequestInfo);
            
            try {
                return supplier.get();
            } catch (Exception e) {
                // 捕获并处理异常
                exceptionHandler.accept(e);
                throw e; // 重新抛出异常以便CompletableFuture能够处理
            }
            finally {
                // 清理上下文
                TaskContextHolder.clearTaskContext();
//                RequestHolder.remove();
            }
        }, executor);
    }

    /**
     * 带扩展信息的任务执行
     */
    public static CompletableFuture<Void> safeRunAsyncWithExtInfo(String taskName, String taskId, 
                                                                 Runnable runnable, Executor executor, 
                                                                 String extInfoKey, Object extInfoValue) {
        TaskContext parentContext = new TaskContext(taskName, taskId);
        parentContext.addExtInfo(extInfoKey, extInfoValue);
        RequestInfo parentRequestInfo = RequestHolder.get();
        
        return CompletableFuture.runAsync(() -> {
            TaskContextHolder.setTaskContext(parentContext);
//            TaskContextHolder.copyRequestInfoFromParent(parentRequestInfo);
            
            try {
                runnable.run();
            } catch (Exception e) {
                // 捕获并处理异常
                ENHANCED_EXCEPTION_HANDLER.accept(e);
                throw e; // 重新抛出异常以便CompletableFuture能够处理
            }
            finally {
                TaskContextHolder.clearTaskContext();
//                RequestHolder.remove();
            }
        }, executor);
    }

    /**
     * 安全执行带返回值的异步任务，异常时返回默认值
     */
    public static <T> CompletableFuture<T> safeSupplyAsyncWithDefault(String taskName, String taskId, 
                                                                     Supplier<T> supplier, T defaultValue, Executor executor) {
        return safeSupplyAsync(taskName, taskId, supplier, executor)
                .exceptionally(throwable -> {
                    String taskInfo = TaskContextHolder.getCurrentTaskInfo();
                    log.warn("异步任务执行失败，返回默认值: {} {}", defaultValue, taskInfo, throwable);
                    return defaultValue;
                });
    }

    /**
     * 批量执行异步任务，统一异常处理
     */
    public static CompletableFuture<Void> allOfSafe(CompletableFuture<?>... futures) {
        return CompletableFuture.allOf(futures)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        ENHANCED_EXCEPTION_HANDLER.accept(throwable);
                    }
                });
    }
}