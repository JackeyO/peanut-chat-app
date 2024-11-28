package com.sici.chat.im.core.server.handler;

import com.sici.chat.model.im.bo.ImMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:23 PM
 * @version: 1.0
 */

public interface AbstractMessageHandler {
    /**
     * 消息处理
     * @param ctx
     * @param imMsg
     */
    void handle(ChannelHandlerContext ctx, ImMsg imMsg);
}
