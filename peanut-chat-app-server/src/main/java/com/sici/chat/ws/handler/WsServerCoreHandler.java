package com.sici.chat.ws.handler;

import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.handler
 * @author: 20148
 * @description: Im Handler
 * @create-date: 9/16/2024 3:14 PM
 * @version: 1.0
 */

@Component
@Slf4j
@ChannelHandler.Sharable
public class WsServerCoreHandler extends SimpleChannelInboundHandler<ImMsg> {
    @Resource
    private WebSocketService webSocketService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImMsg msg) {
        AbstractWsMessageHandler wsMessageHandler = WsMessageHandlerFactory.getWsMessageHandler(msg.getType());
        wsMessageHandler.handle(ctx, msg);
    }

    /**
     * 正常或意外短线都会回调到这里
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        userOffline(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 没有接收到心跳包,就下线
            if (idleStateEvent.state().equals(IdleState.READER_IDLE)) {
                userOffline(ctx.channel());
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            this.webSocketService.connect(ctx.channel());
            // ws连接建立后尝试机型token鉴权
            this.webSocketService.tokenAuthorize(ctx.channel());
        }
        super.userEventTriggered(ctx, evt);
    }

    private void userOffline(Channel channel) {
        webSocketService.userOffline(channel);
    }
}


