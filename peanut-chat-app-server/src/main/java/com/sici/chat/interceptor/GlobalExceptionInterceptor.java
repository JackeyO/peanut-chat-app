package com.sici.chat.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sici.chat.exception.BusinessException;
import com.sici.common.result.ResponseResult;

/**
 * 全局异常处理器
 */
@Slf4j
public class GlobalExceptionInterceptor {
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常：{}", e.getMessage(), e);
        return ResponseResult.errorResult(400, "参数异常");
    }

    /**
     * 处理其他未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return ResponseResult.errorResult(500, "系统内部错误");
    }
} 