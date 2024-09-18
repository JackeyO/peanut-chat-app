package com.sici.live.im.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:47 PM
 * @version: 1.0
 */

@Slf4j
public class ImClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMsg imMsg = (ImMsg) msg;
        log.info("[server response]: " + imMsg);
    }
}
