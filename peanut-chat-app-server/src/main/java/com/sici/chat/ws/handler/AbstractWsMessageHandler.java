package com.sici.chat.ws.handler;

import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.model.ws.bo.ImMsgReq;
import com.sici.common.enums.chat.message.MessageReqTypeEnum;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
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
    public abstract void handle(ChannelHandlerContext ctx, ImMsgReq msgReq);
    public abstract MessageReqTypeEnum getSupportedMessageType();
    public AbstractWsMessageHandler() {
        WsMessageHandlerFactory.registerWsMessageHandler(this);
    }
}