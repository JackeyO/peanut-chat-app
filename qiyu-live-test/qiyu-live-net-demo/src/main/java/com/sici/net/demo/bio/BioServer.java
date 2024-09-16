package com.sici.net.demo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.net.demo.bio
 * @author: 20148
 * @description:
 * @create-date: 9/15/2024 7:53 PM
 * @version: 1.0
 */

public class BioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9090));

        Socket socket = serverSocket.accept();

        while (true) {
            InputStream is = socket.getInputStream();

            byte[] bytes = new byte[10];

            is.read(bytes);

            System.out.println("receive: " + new String(bytes));
        }
    }
}
