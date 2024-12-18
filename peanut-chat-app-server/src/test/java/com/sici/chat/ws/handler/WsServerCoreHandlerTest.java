package com.sici.chat.ws.handler;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

class WsServerCoreHandlerTest {
    @Test
    void testConnect() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 9999));

        SocketChannel channel = socket.getChannel();
//        channel.
    }
}