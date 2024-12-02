package com.sici.net.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.net.demo.nio
 * @author: 20148
 * @description:
 * @create-date: 9/15/2024 8:03 PM
 * @version: 1.0
 */

public class NioServer {
    public static List<SocketChannel> acceptChannelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(9090));

        new Thread(() -> {
            while (true) {
                for (SocketChannel socketChannel : acceptChannelList) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(20);

                    try {
                        socketChannel.read(byteBuffer);
                        System.out.println("receive: " + new String(byteBuffer.array()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        while (true) {
            SocketChannel accept = serverSocketChannel.accept();
            if (accept != null) {
                accept.configureBlocking(false);
                acceptChannelList.add(accept);
            }
        }
    }
}
