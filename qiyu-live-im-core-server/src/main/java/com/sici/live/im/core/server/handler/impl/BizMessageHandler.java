package com.sici.live.im.core.server.handler.impl;

import com.sici.live.im.core.server.common.ImMsg;
import com.sici.live.im.core.server.handler.AbstractMessageHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler.impl
 * @author: 20148
 * @description: 业务消息处理器
 * @create-date: 9/16/2024 3:24 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class BizMessageHandler implements AbstractMessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {

    }
}
