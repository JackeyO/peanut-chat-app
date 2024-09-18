package com.sici.live.im.core.server.handler.impl;

import com.alibaba.fastjson.JSON;
import com.sici.live.im.core.server.common.ChannelHandlerContextCache;
import com.sici.live.im.core.server.common.ImMsg;
import com.sici.live.im.core.server.common.util.ImCacheUtil;
import com.sici.live.im.core.server.common.util.ImContextUtil;
import com.sici.live.im.core.server.handler.AbstractMessageHandler;
import com.sici.live.model.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler.impl
 * @author: 20148
 * @description: 登出消息处理器
 * @create-date: 9/16/2024 3:24 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class LogoutMessageHandler implements AbstractMessageHandler {
    @Resource
    private ImCacheUtil imCacheUtil;
    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {
        Long userId = ImContextUtil.getUserId(ctx);
        if (userId == null) {
            throw new IllegalArgumentException("[logoutMessageHandler]==>userId is null");
        }

        byte[] body = imMsg.getBody();
        if (body != null) {
            String bodyStr = new String(body);
            ImMsgBody imMsgBody = JSON.parseObject(bodyStr, ImMsgBody.class);
            if (imMsgBody != null) {
                imCacheUtil.removeImOnlineTime(userId, imMsgBody.getAppId());
                imCacheUtil.removeImBindAddressOfUserId(userId, imMsgBody.getAppId());
            }
        }
        ChannelHandlerContextCache.remove(userId);

        ctx.close();
    }
}
