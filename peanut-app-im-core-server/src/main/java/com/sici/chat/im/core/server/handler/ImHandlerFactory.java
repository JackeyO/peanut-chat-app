package com.sici.chat.im.core.server.handler;

import com.sici.chat.im.core.server.common.ImMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:27 PM
 * @version: 1.0
 */

public interface ImHandlerFactory {
    /**
     * 做消息处理分发
     * @param ctx
     * @param imMsg
     */
    void doMessageHandle(ChannelHandlerContext ctx, ImMsg imMsg);
}
