package com.sici.live.im.core.server;

import com.alibaba.fastjson.JSON;
import com.sici.common.enums.im.AppIdEnums;
import com.sici.common.result.ResponseResult;
import com.sici.live.im.core.server.common.ImMsgBuilder;
import com.sici.live.im.core.server.common.ImMsgDecoder;
import com.sici.live.im.core.server.common.ImMsgEncoder;
import com.sici.live.interfaces.im.rpc.ImTokenRpc;
import com.sici.live.model.im.dto.ImMsgBody;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 8:39 PM
 * @version: 1.0
 */

@Slf4j
@Component
public class ImClientTest implements InitializingBean {
    @Resource
    private ImTokenRpc imTokenRpc;

    @Override
    public void afterPropertiesSet() {
        testImClient();
    }
    public void testImClient() {
        CompletableFuture.runAsync(() ->{
            try {
                startConnection("127.0.0.1", 8085);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void startConnection(String address, int port) throws InterruptedException {
        Map<Long, Channel> userChannelMap = new HashMap<>();
        for (int i = 0; i < 5; i ++) {
            EventLoopGroup clientGroup = new NioEventLoopGroup();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    log.info("connection established");
                    ch.pipeline().addLast(new ImMsgEncoder());
                    ch.pipeline().addLast(new ImMsgDecoder());
                    ch.pipeline().addLast(new ImClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
            Channel channel = channelFuture.channel();

            Long userId = (long) (10000 + i);
            ResponseResult<String> imLoginToken = imTokenRpc.createImLoginToken(userId, AppIdEnums.QIYU_LIVE_APP.getAppId());
            String token = imLoginToken.getData();

            ImMsgBody imMsgBody = ImMsgBody.builder()
                    .userId(userId)
                    .appId(AppIdEnums.QIYU_LIVE_APP.getAppId())
                    .token(token)
                    .data("hello, login:" + userId)
                    .build();
            channel.writeAndFlush(ImMsgBuilder.buildLogin(JSON.toJSONString(imMsgBody)));
            userChannelMap.put(userId, channel);
            Thread.sleep(1000);
        }

        while (true) {
            userChannelMap.forEach((k, v) -> {
                ImMsgBody imMsgBody = ImMsgBody.builder()
                        .userId(k)
                        .appId(AppIdEnums.QIYU_LIVE_APP.getAppId())
                        .data("hello, heart:" + k)
                        .build();
                v.writeAndFlush(ImMsgBuilder.buildHeartBeat(JSON.toJSONString(imMsgBody)));
            });
            Thread.sleep(1000);
        }
    }
}
