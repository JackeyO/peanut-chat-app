package com.sici.live.im.core.server.starter;

import com.sici.live.im.core.server.common.ImMsgDecoder;
import com.sici.live.im.core.server.common.ImMsgEncoder;
import com.sici.live.im.core.server.handler.ImServerCoreHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author idea
 * @Date: Created in 20:35 2023/7/9
 * @Description
 */
@Configuration
@Slf4j
public class WsNettyImServerStarter implements InitializingBean {
    //指定监听的端口
    @Value("${qiyu.im.ws.port}")
    private int port;
    @Resource
    private ImServerCoreHandler wsImServerCoreHandler;

    //基于netty去启动一个java进程，绑定监听的端口
    public void startApplication() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new ChannelInitializer<>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                log.info("channel initialized");

                // 增加编解码器
                channel.pipeline().addLast(new ImMsgEncoder());
                channel.pipeline().addLast(new ImMsgDecoder());

                // 设置消息处理器handler
                channel.pipeline().addLast(new ImServerCoreHandler());
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }));

        log.info("netty started successfully, bind port: " + port);

        channelFuture.channel().closeFuture().sync();
//
//
//        //获取im的服务注册ip和暴露端口
//        String registryIp = environment.getProperty("DUBBO_IP_TO_REGISTRY");
//        String registryPort = environment.getProperty("DUBBO_PORT_TO_REGISTRY");
//        if (StringUtils.isEmpty(registryPort) || StringUtils.isEmpty(registryIp)) {
//            throw new IllegalArgumentException("启动参数中的注册端口和注册ip不能为空");
//        }
//        ChannelHandlerContextCache.setServerIpAddress(registryIp + ":" + registryPort);
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
        nettyServerThread.setName("qiyu-live-im-server-ws");
        nettyServerThread.start();
    }
}
