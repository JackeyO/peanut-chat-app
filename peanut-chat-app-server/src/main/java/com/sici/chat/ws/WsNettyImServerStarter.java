package com.sici.chat.ws;

import com.sici.chat.ws.handler.WsServerCoreHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author idea
 * @Date: Created in 20:35 2023/7/9
 * @Description
 */
@Configuration
@Slf4j
public class WsNettyImServerStarter implements InitializingBean {
    //指定监听的端口
    @Value("${peanut.ws.port}")
    private int port;
    @Resource
    private ApplicationContext applicationContext;

    //基于netty去启动一个java进程，绑定监听的端口
    public void startApplication() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new ChannelInitializer<>() {
            @Override
            protected void initChannel(Channel channel) {
                log.info("channel initialized, channel:{}", channel);
                // 设置消息处理器handler
                channel.pipeline().addLast(new IdleStateHandler(30, 0, 0));
                channel.pipeline().addLast(applicationContext.getBean(WsServerCoreHandler.class));
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            log.info("netty stopped gracefully!");
        }));

        log.info("netty started successfully!, bind port: " + port);

        channelFuture.channel().closeFuture().sync();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread nettyServerThread = new Thread(() -> {
            try {
                startApplication();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        nettyServerThread.setName("peanut-chat-app-server-ws");
        nettyServerThread.start();
    }
}
