package com.sici.chat.interceptor;

import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.interceptor
 * @author: 20148
 * @description: 请求上下文信息拦截器
 * @create-date: 12/27/2024 6:06 PM
 * @version: 1.0
 */

@Component
@Order(2)
public class RequestInfoCollectInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 设置RequestInfo到RequestHolder中  || created by 20148 at 12/27/2024 6:07 PM
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO: 从RequestHolder中移除  || created by 20148 at 12/27/2024 6:07 PM
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}