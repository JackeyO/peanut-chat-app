package com.sici.chat.interceptor;

import com.sici.chat.context.RequestInfo;
import com.sici.chat.util.JwtUtil;
import com.sici.common.constant.common.RequestConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;


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
        String authorization = request.getHeader(RequestConstant.AUTHORIZATION_HEADER);
        String remoteHost = request.getRemoteHost();
        // 分理出Bearer后面的部分
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            Long userId = JwtUtil.getUidFromToken(token);
            if (Objects.isNull(userId)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            request.setAttribute(RequestConstant.REQUEST_INFO_ATTRIBUTE,
                    RequestInfo.builder()
                            .userId(userId)
                            .ip(remoteHost)
                            .build());

            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
