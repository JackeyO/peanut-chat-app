package com.sici.chat.im.client.ws;

import com.alibaba.fastjson.JSON;
import com.sici.chat.model.chat.message.vo.LoginMessageVo;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.model.ws.bo.ImMsgReq;
import com.sici.common.enums.chat.message.MessageReqTypeEnum;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.framing.TextFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

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
        WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost:9999"), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                log.info("shake successfully!");
            }

            @Override
            public void onMessage(String s) {
                ImMsg<LoginMessageVo> imMsg = JSON.parseObject(s, ImMsg.class);
                LoginMessageVo data = imMsg.getData();
                log.info("receive : " + s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                log.info("close");
            }

            @Override
            public void onError(Exception e) {
                log.error("error");
            }
        };

        try {
            webSocketClient.connect();
            //等待服务端响应
            while (!webSocketClient.getReadyState().equals(ReadyState.OPEN)) {
                log.info("connecting");
                Thread.sleep(1000);
            }
            log.info("connect successfully!");
            //向WebSocket服务端发送数据
            webSocketClient.send(JSON.toJSONString(getLoginImMsgReq()));
            log.info("login request send successfully");
            while (true) {

            }
            //关闭连接
//            webSocketClient.close();
        } catch (Exception e) {

        }
    }
}
