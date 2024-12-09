package com.sici.chat.ws.handler;

import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.service.WebSocketService;
import com.sici.common.enums.chat.message.MessageReqTypeEnum;
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
    public MessageReqTypeEnum getSupportedMessageType() {
        return MessageReqTypeEnum.LOIN_REQUEST;
    }

    /**
     * 处理客户端登录请求
     * @param ctx
     * @param imMsg
     */
    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {
        this.webSocketService.handlerLoginReq(ctx);
    }
}