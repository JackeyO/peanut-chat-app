package com.sici.live.im.core.server;

import com.sici.live.im.core.server.common.ImMsg;
import com.sici.live.im.core.server.common.ImMsgBuilder;
import com.sici.live.im.core.server.common.ImMsgDecoder;
import com.sici.live.im.core.server.common.ImMsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:41 PM
 * @version: 1.0
 */

@Slf4j
public class ImClientApplication {
    public static void main(String[] args) throws InterruptedException {
        ImClientApplication imClientApplication = new ImClientApplication();
        imClientApplication.startConnection("127.0.0.1", 9090);
    }

    private void startConnection(String address, int port) throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                log.info("connection established");
                ch.pipeline().addLast(new ImMsgEncoder());
                ch.pipeline().addLast(new ImMsgDecoder());
                ch.pipeline().addLast(new ImClientHandler());
            }
        });


        ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
        Channel channel = channelFuture.channel();

        for (int i = 0; i < 100; i ++) {
            channel.writeAndFlush(ImMsgBuilder.buildLogin("login message"));
            Thread.sleep(1000);
        }
    }
}