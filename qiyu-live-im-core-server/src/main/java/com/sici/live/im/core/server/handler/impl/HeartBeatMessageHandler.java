package com.sici.live.im.core.server.handler.impl;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.util.TimeUtil;
import com.sici.common.constant.im.ImConstant;
import com.sici.framework.redis.CacheService;
import com.sici.live.im.core.server.common.ImMsg;
import com.sici.live.im.core.server.common.ImMsgBuilder;
import com.sici.live.im.core.server.common.util.ImContextUtil;
import com.sici.live.im.core.server.handler.AbstractMessageHandler;
import com.sici.live.im.core.server.redis.key.HeartBeatMessageKeyBuilder;
import com.sici.live.model.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
    private CacheService cacheService;
    @Resource
    private HeartBeatMessageKeyBuilder heartBeatMessageKeyBuilder;
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
        recordImOnlineTime(userId, appId);
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

    private void recordImOnlineTime(Long userId, Integer appId) {
        long currentMillis = System.currentTimeMillis();
        String zSetKey = heartBeatMessageKeyBuilder.buildHearBeatKey(userId, appId);
        cacheService.expire(zSetKey, 5, TimeUnit.MINUTES);
        cacheService.zAdd(zSetKey,
                JSON.toJSONString(userId), currentMillis);

        // 删除两个心跳间隔外的心跳包记录(考虑到网络延迟)
        cacheService.zRemoveRangeByScore(zSetKey, 0, currentMillis - ImConstant.DEFAULT_HEART_BEAT_INTERVAL * 2);
    }
}