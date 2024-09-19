package com.sici.live.im.client;

import com.alibaba.fastjson.JSON;
import com.sici.common.enums.im.AppIdEnums;
import com.sici.common.enums.im.ImMsgBizCodeEnum;
import com.sici.common.result.ResponseResult;
import com.sici.live.interfaces.im.rpc.ImTokenRpc;
import com.sici.live.model.im.dto.ImMsgBody;
import com.sici.live.model.im.dto.ImMsgDto;
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
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
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
    @DubboReference(timeout = 60000, retries = 0)
    private ImTokenRpc imTokenRpc;

    @Override
    public void afterPropertiesSet() {
        testImClient();
    }

    public void testImClient() {
        CompletableFuture.runAsync(() -> {
            try {
                startChat("127.0.0.1", 8085);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void startConnection(String address, int port) throws InterruptedException {
        Map<Long, Channel> userChannelMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
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
                    .data("login:" + userId)
                    .build();
            channel.writeAndFlush(ImMsgBuilder.buildLogin(JSON.toJSONString(imMsgBody)));
            userChannelMap.put(userId, channel);
            Thread.sleep(1000);
        }

        Thread.sleep(5000);
        while (true) {
            userChannelMap.forEach((k, v) -> {
                ImMsgBody imMsgBody = ImMsgBody.builder()
                        .userId(k)
                        .appId(AppIdEnums.QIYU_LIVE_APP.getAppId())
                        .bizCode(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode())
                        .data(JSON.toJSONString(ImMsgDto.builder()
                                .type(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode())
                                .build()))
                        .build();
                v.writeAndFlush(ImMsgBuilder.buildBiz(JSON.toJSONString(imMsgBody)));
            });
            Thread.sleep(1000);
        }
    }


    public void startChat(String address, Integer port) throws InterruptedException {
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

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户id");
        Long userId = scanner.nextLong();
        System.out.println("请输入用户id");
        Long objectId = scanner.nextLong();

        ResponseResult<String> imLoginToken = imTokenRpc.createImLoginToken(userId, AppIdEnums.QIYU_LIVE_APP.getAppId());
        String token = imLoginToken.getData();

        Integer appId = AppIdEnums.QIYU_LIVE_APP.getAppId();
        ImMsgBody imMsgBody = ImMsgBody.builder()
                .userId(userId)
                .appId(appId)
                .token(token)
                .data("login:" + userId)
                .build();
        channel.writeAndFlush(ImMsgBuilder.buildLogin(JSON.toJSONString(imMsgBody)));

//        CompletableFuture.runAsync(() -> {
//            while (true) {
//                ImMsg imMsg = ImMsgBuilder.buildHeartBeat(JSON.toJSONString(
//                        ImMsgBody.builder()
//                                .userId(userId)
//                                .appId(appId)
//                                .token(token)
//                                .build()
//                ));
//                channel.writeAndFlush(imMsg);
//
//                try {
//                    Thread.sleep(30000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

        while (true) {
            System.out.println("请输入消息内容");
            String message = scanner.nextLine();

            ImMsg imMsg = ImMsgBuilder.buildBiz(JSON.toJSONString(
                    ImMsgBody.builder()
                            .userId(objectId)
                            .appId(appId)
                            .msgId(UUID.randomUUID().toString())
                            .bizCode(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode())
                            .data(JSON.toJSONString(
                                    ImMsgDto.builder()
                                            .content(message)
                                            .build()
                            ))
                            .build()
            ));

            channel.writeAndFlush(imMsg);
        }
    }
}
