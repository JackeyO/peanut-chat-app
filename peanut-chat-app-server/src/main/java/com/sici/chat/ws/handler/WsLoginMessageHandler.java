package com.sici.chat.ws.handler;

import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.ws.service.WebSocketService;
import com.sici.common.enums.im.ImMsgTypeEnums;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.handler.impl
 * @author: 20148
 * @description: 登录消息处理器
 * @create-date: 9/16/2024 3:24 PM
 * @version: 1.0
 */


@Component
@Slf4j
public class WsLoginMessageHandler extends AbstractWsMessageHandler {
    @Resource
    private WebSocketService webSocketService;
    @Override
    public ImMsgTypeEnums getSupportedMessageType() {
        return ImMsgTypeEnums.IM_MSG_LOGIN;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {
    }
}
