package com.sici.chat.im.core.server.starter;

import com.sici.common.constant.im.ImConstant;
import com.sici.chat.im.core.server.common.ChannelHandlerContextCache;
import com.sici.chat.im.core.server.handler.ImServerCoreHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

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
    @Value("${qiyu.im.ws.port}")
    private int port;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private Environment environment;

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
                channel.pipeline().addLast(applicationContext.getBean(ImServerCoreHandler.class));
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            log.info("netty stopped gracefully!");
        }));

        log.info("netty started successfully!, bind port: " + port);

        //获取im的服务注册ip和暴露端口
        String registryIp = environment.getProperty(ImConstant.DUBBO_REGISTRY_IP_ENV_NAME);
        String registryPort = environment.getProperty(ImConstant.DUBBO_REGISTRY_PORT_ENV_NAME);
        if (StringUtils.isEmpty(registryPort) || StringUtils.isEmpty(registryIp)) {
            throw new IllegalArgumentException("registry ip and port can't be null");
        }
        ChannelHandlerContextCache.setServerAddress(registryIp + ":" + registryPort);
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
        nettyServerThread.setName("qiyu-live-im-server-ws");
        nettyServerThread.start();
    }
}
