package com.sici.chat.im.client.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.im.client.ws
 * @author: 20148
 * @description:
 * @create-date: 12/18/2024 4:22 PM
 * @version: 1.0
 */

public class WebSocketClientTest {
    public static void main(String[] args) throws URISyntaxException {
        WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost:9999"), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {

            }

            @Override
            public void onMessage(String s) {

            }

            @Override
            public void onClose(int i, String s, boolean b) {

            }

            @Override
            public void onError(Exception e) {

            }
        };

        webSocketClient.connect();
    }
}
