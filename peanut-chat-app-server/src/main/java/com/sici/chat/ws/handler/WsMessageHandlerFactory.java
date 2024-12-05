package com.sici.chat.ws.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.handler.impl
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:28 PM
 * @version: 1.0
 */

@Component
public class WsMessageHandlerFactory {
    private static Map<Integer, AbstractWsMessageHandler> wsMessageHandlers = new HashMap<>();

    public static void registerWsMessageHandler(AbstractWsMessageHandler messageHandler) {
        wsMessageHandlers.put(messageHandler.getSupportedMessageType().getCode(), messageHandler);
    }

    public static AbstractWsMessageHandler getWsMessageHandler(Integer wsMsgType) {
        return wsMessageHandlers.get(wsMsgType);
    }
}