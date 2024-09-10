package com.sici.common.log.advice;

import com.alibaba.fastjson.JSON;
import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.common.log.constant.LogConstant;
import com.sici.common.log.util.LoggerUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @projectName: BootTemplate
 * @package: org.sici.log.annotation.log.advice
 * @author: 20148
 * @description: 日志输出切面
 * @create-date: 8/6/2024 8:39 PM
 * @version: 1.0
 */

@Aspect
@Component
public class ServiceLogAdvice {
    @Pointcut("@annotation(com.sici.common.log.annotation.EnableInvokeLog)")
    public void serviceLogPointCut() {
    }

    @Before("serviceLogPointCut()")
    public void beforeLog(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        EnableInvokeLog annotation = method.getAnnotation(EnableInvokeLog.class);

        Logger logger = LoggerUtil.getLogger(method.getDeclaringClass());

        logger.info(LogConstant.SEPARATOR + annotation.description() + " in-params: {}", JSON.toJSONString(joinPoint.getArgs()));
    }
    @AfterReturning(value = "serviceLogPointCut()", returning = "result")
    public void afterLog(JoinPoint joinPoint, Object result) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        EnableInvokeLog annotation = method.getAnnotation(EnableInvokeLog.class);

        Logger logger = LoggerUtil.getLogger(method.getDeclaringClass());

        logger.info(LogConstant.SEPARATOR + annotation.description() + " out-params: {}", JSON.toJSONString(result));
    }
}
