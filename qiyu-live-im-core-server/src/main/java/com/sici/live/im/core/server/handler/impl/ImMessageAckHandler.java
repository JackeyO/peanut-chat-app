package com.sici.live.im.core.server.handler.impl;

import com.alibaba.fastjson.JSON;
import com.sici.live.im.core.server.common.ImMsg;
import com.sici.live.im.core.server.common.util.ImContextUtil;
import com.sici.live.im.core.server.handler.AbstractMessageHandler;
import com.sici.live.im.core.server.service.ImMsgAckService;
import com.sici.live.model.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler.impl
 * @author: 20148
 * @description:
 * @create-date: 9/19/2024 2:29 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class ImMessageAckHandler implements AbstractMessageHandler {
    @Resource
    private ImMsgAckService imMsgAckService;
    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {
        Long userId = ImContextUtil.getUserId(ctx);
        Integer appId  = ImContextUtil.getAppId(ctx);
        if (userId == null || appId == null) {
            log.error("[im-core-server]==>[ImMessageAckHandler]==>连接无效");
            ctx.close();
            return ;
        }

        byte[] body = imMsg.getBody();
        ImMsgBody imMsgBody = JSON.parseObject(new String(body), ImMsgBody.class);

        // 已收到消息，执行确认
        imMsgAckService.doMsgAck(imMsgBody);
        log.info("[im-core-server]==>执行消息ACK成功, imMsgBody:{}", imMsgBody);
    }
}

