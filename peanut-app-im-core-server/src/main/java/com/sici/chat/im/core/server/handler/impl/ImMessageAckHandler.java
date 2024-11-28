package com.sici.chat.im.core.server.handler.impl;

import com.sici.chat.model.im.bo.ImMsg;
import com.sici.chat.im.core.server.common.util.ImContextUtil;
import com.sici.chat.im.core.server.handler.AbstractMessageHandler;
import com.sici.chat.im.core.server.service.ImMsgAckService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
        Integer userId = ImContextUtil.getUserId(ctx);
        if (userId == null) {
            log.error("[im-core-server]==>[ImMessageAckHandler]==>连接无效");
            ctx.close();
            return ;
        }

        // 已收到消息，执行确认
        imMsgAckService.doMsgAck(imMsg);
        log.info("[im-core-server]==>执行消息ACK成功, imMsg:{}", imMsg);
    }
}

