package com.sici.chat.ws.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sici.chat.model.ws.bo.ImMsgReq;
import com.sici.chat.service.ws.WebSocketService;
import com.sici.common.enums.chat.message.MessageReqTypeEnum;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

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
    public MessageReqTypeEnum getSupportedMessageType() {
        return MessageReqTypeEnum.LOIN_REQUEST;
    }

    /**
     * 处理客户端登录请求
     * @param ctx
     * @param msgReq
     */
    @Override
    public void handle(ChannelHandlerContext ctx, ImMsgReq msgReq) {
        this.webSocketService.handlerLoginReq(ctx, msgReq);
    }
}