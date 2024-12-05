package com.sici.chat.ws.handler;

import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.common.enums.im.ImMsgTypeEnums;
import io.netty.channel.ChannelHandlerContext;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.handler
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:23 PM
 * @version: 1.0
 */

public abstract class AbstractWsMessageHandler {
    /**
     * 消息处理
     * @param ctx
     * @param imMsg
     */
    public abstract void handle(ChannelHandlerContext ctx, ImMsg imMsg);
    public abstract ImMsgTypeEnums getSupportedMessageType();
    public AbstractWsMessageHandler() {
        WsMessageHandlerFactory.registerWsMessageHandler(this);
    }
}