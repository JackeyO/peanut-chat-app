package com.sici.net.demo.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.net.demo.bio
 * @author: 20148
 * @description:
 * @create-date: 9/15/2024 7:56 PM
 * @version: 1.0
 */

public class BioClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();

        socket.connect(new InetSocketAddress(9090));

        OutputStream outputStream = socket.getOutputStream();

        while (true) {
            outputStream.write("test".getBytes());
            outputStream.flush();
            Thread.sleep(1000);
        }
    }
}
