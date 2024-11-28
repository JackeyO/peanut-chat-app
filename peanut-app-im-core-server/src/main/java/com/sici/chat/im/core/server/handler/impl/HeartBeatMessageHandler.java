package com.sici.chat.im.core.server.handler.impl;

import com.alibaba.fastjson.JSON;
import com.sici.chat.model.im.bo.ImMsg;
import com.sici.chat.im.core.server.common.ImMsgBuilder;
import com.sici.chat.im.core.server.common.util.ImCacheUtil;
import com.sici.chat.im.core.server.common.util.ImContextUtil;
import com.sici.chat.im.core.server.handler.AbstractMessageHandler;
import com.sici.chat.model.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler.impl
 * @author: 20148
 * @description: 心跳消息处理器
 * @create-date: 9/16/2024 3:24 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class HeartBeatMessageHandler implements AbstractMessageHandler {
    @Resource
    private ImCacheUtil imCacheUtil;
    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {
        // 心跳包校验
        Long userId = ImContextUtil.getUserId(ctx);
        Integer appId = ImContextUtil.getAppId(ctx);

        if (userId == null || appId == null) {
            log.error("[im-HeartBeatImMsgHandler]==>message error, message:{}", imMsg);
            ctx.close();
            return;
        }
        // 心跳包record记录
        imCacheUtil.recordImOnlineTime(userId, appId);
        imCacheUtil.recordImBindAddressOfUserId(userId, appId);

        log.info("[im-HeartBeatImMsgHandler]==>ImMsg is " + imMsg);

        ImMsg imMsgResponse = ImMsgBuilder.buildHeartBeat(JSON.toJSONString(
                ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .data("true")
                        .build()
        ));

        ctx.writeAndFlush(imMsgResponse);
    }
}