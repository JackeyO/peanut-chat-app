package com.sici.chat.interceptor;

import com.sici.chat.context.RequestInfo;
import com.sici.chat.util.RequestHolder;
import com.sici.common.constant.common.RequestConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


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
        RequestHolder.set((RequestInfo) request.getAttribute(RequestConstant.REQUEST_INFO_ATTRIBUTE));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}