package com.sici.chat.ws;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.sici.chat.ws.handler.WsServerCoreHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

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

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) {
                log.info("channel initialized, channel:{}", channel);
                // 设置消息处理器handler
                 channel.pipeline().addLast(new IdleStateHandler(30, 0, 0));

//                 因为使用http协议，所以需要使用http的编码器，解码器
                channel.pipeline().addLast(new HttpServerCodec());
                // 以块方式写，添加 chunkedWriter 处理器
                channel.pipeline().addLast(new ChunkedWriteHandler());
                /**
                 * 说明：
                 *  1. http数据在传输过程中是分段的，HttpObjectAggregator可以把多个段聚合起来；
                 *  2. 这就是为什么当浏览器发送大量数据时，就会发出多次 http请求的原因
                 */
                channel.pipeline().addLast(new HttpObjectAggregator(8192));
                
                // 添加WebSocket异常处理器
                channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                        log.error("WebSocket error: {}", cause.getMessage(), cause);
                        ctx.close();
                    }
                });
                
                // 设置WebSocket路径处理器，并启用详细日志
                channel.pipeline().addLast(new WebSocketServerProtocolHandler("/", null, true) {
                    @Override
                    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
                        log.info("WebSocket protocol handler received frame: {}", frame.getClass().getSimpleName());
                        super.decode(ctx, frame, out);
                    }
                });
                
                channel.pipeline().addLast(applicationContext.getBean(WsServerCoreHandler.class));
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            log.info("netty stopped gracefully!");
        }));

        log.info("netty 启动成功, 绑定端口: {}", port);
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
