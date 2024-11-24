package com.sici.chat.im.core.server.handler;

import com.sici.common.enums.im.AppIdEnums;
import com.sici.chat.im.core.server.common.ChannelHandlerContextCache;
import com.sici.chat.im.core.server.common.ImMsg;
import com.sici.chat.im.core.server.common.util.ImCacheUtil;
import com.sici.chat.im.core.server.common.util.ImContextUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler
 * @author: 20148
 * @description: Im Handler
 * @create-date: 9/16/2024 3:14 PM
 * @version: 1.0
 */

@Component
@Slf4j
@ChannelHandler.Sharable
public class ImServerCoreHandler extends SimpleChannelInboundHandler {
    @Resource
    private ImHandlerFactory imHandlerFactory;
    @Resource
    private ImCacheUtil imCacheUtil;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof ImMsg)) {
            throw new RuntimeException("error type message: " + msg);
        }

        ImMsg imMsg = (ImMsg) msg;

        int code = imMsg.getCode();
        // 根据code类型交给不同的handler去处理
        imHandlerFactory.doMessageHandle(ctx, imMsg);
    }

    /**
     * 正常或意外短线都会回调到这里
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Long userId = ImContextUtil.getUserId(ctx);

        if (userId != null) {
            imCacheUtil.removeImOnlineTime(userId, AppIdEnums.QIYU_LIVE_APP.getAppId());
            imCacheUtil.removeImBindAddressOfUserId(userId, AppIdEnums.QIYU_LIVE_APP.getAppId());
        }

        ChannelHandlerContextCache.remove(userId);
    }
}


