package com.sici.chat.im.client.ws;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.sici.chat.model.chat.message.vo.LoginMessageVo;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.model.ws.bo.ImMsgReq;
import com.sici.common.enums.chat.message.MessageReqTypeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.im.webSocketClient.ws
 * @author: 20148
 * @description:
 * @create-date: 12/18/2024 4:22 PM
 * @version: 1.0
 */

@Slf4j
public class WebSocketClientTest {
    public static ImMsgReq getLoginImMsgReq() {
        ImMsgReq<Object> imMsgReq = new ImMsgReq<>();
        imMsgReq.setType(MessageReqTypeEnum.LOIN_REQUEST.getType());
        return imMsgReq;
    }

    public static void main(String[] args) throws URISyntaxException {
        WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost:9999")) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                log.info("shake successfully! Status: {}", serverHandshake.getHttpStatus());
            }

            @Override
            public void onMessage(String s) {
                
                try {
                    ImMsg<LoginMessageVo> imMsg = JSON.parseObject(s, ImMsg.class);
                    LoginMessageVo data = imMsg.getData();
                    log.info("receive : " + s);
                } catch (Exception e) {
                    log.error("Error parsing message: {}", s, e);
                }
            }
            @Override
            public void onClose(int code, String reason, boolean remote) {
                log.info("close with code: {}, reason: {}, remote: {}", code, reason, remote);
                if (code == -1) {
                    log.error("Connection closed abnormally. This might be due to handshake failure.");
                }
            }

            @Override
            public void onError(Exception e) {
                log.error("WebSocket error", e);
            }
        };

        try {
            log.info("准备 to connect to WebSocket server...");
            webSocketClient.connect();

            Thread.sleep(3000);
            
            //等待服务端响应
            int retryCount = 0;
            while (!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                log.info("connecting, current state: {}, retry count: {}", 
                    webSocketClient.getReadyState(), 
                    retryCount++);
                if (retryCount > 10) {
                    log.error("Failed to connect after 10 retries");
                    break;
                }
                Thread.sleep(3000);
            }
            
            if (webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                log.info("connect successfully!");
                //向WebSocket服务端发送数据
                webSocketClient.send(JSON.toJSONString(getLoginImMsgReq()));
                log.info("login request send successfully");
                while (true) {
                    Thread.sleep(1000);
                }
            } else {
                log.error("Failed to establish WebSocket connection");
            }
        } catch (Exception e) {
            log.error("Connection error", e);
        }
    }
}
