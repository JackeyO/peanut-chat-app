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
 * @description: 黑名单拦截器
 * @create-date: 12/27/2024 5:59 PM
 * @version: 1.0
 */

@Component
@Order(3)
public class BlackInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 黑名单拦截，实现按用户id和ip拦截两种方式  || created by 20148 at 12/27/2024 6:03 PM
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
