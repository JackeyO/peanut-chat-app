package com.sici.framework.agent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * 用于取消操作的令牌
 */
@Slf4j
public class CancellationToken {
    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private volatile Consumer<CancellationToken> cancellationCallback;
    
    public CancellationToken() {}
    
    public boolean isCancellationRequested() {
        return cancelled.get();
    }
    
    public void cancel() {
        if (cancelled.compareAndSet(false, true)) {
            if (cancellationCallback != null) {
                try {
                    cancellationCallback.accept(this);
                } catch (Exception e) {
                    // 记录日志但不抛出异常
                    log.error("Error in cancellation callback: " + e.getMessage());
                }
            }
        }
    }
    
    public void throwIfCancellationRequested() {
        if (isCancellationRequested()) {
            throw new OperationCancelledException("Operation was cancelled");
        }
    }
    
    public void setCancellationCallback(Consumer<CancellationToken> callback) {
        this.cancellationCallback = callback;
    }
    
    // 静态工厂方法
    public static CancellationToken none() {
        return new CancellationToken();
    }
    
    public static CancellationToken cancelled() {
        CancellationToken token = new CancellationToken();
        token.cancel();
        return token;
    }
    
    // 自定义异常
    public static class OperationCancelledException extends RuntimeException {
        public OperationCancelledException(String message) {
            super(message);
        }
    }
}