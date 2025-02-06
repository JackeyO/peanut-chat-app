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
 * @description: token拦截器
 * @create-date: 12/27/2024 5:51 PM
 * @version: 1.0
 */

/**
 * 先于BlackInterceptor和RequestInfoCollectInterceptor执行
 * @see com.sici.chat.interceptor.BlackInterceptor
 * @see com.sici.chat.interceptor.RequestInfoCollectInterceptor
 */
@Component
@Order(1)
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 获取token，检查访问路径是否合法，设置attribute,并把信息放入RequestHolder中  || created by 20148 at 12/27/2024 5:52 PM
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
