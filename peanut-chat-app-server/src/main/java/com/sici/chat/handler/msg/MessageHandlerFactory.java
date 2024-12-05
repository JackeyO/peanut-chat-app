package com.sici.chat.handler.msg;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 消息处理器工厂类
 * @create-date: 11/25/2024 5:31 PM
 * @version: 1.0
 */

public class MessageHandlerFactory {
    public static Map<Integer, AbstractMessageHandler> messageHandlerMap = new HashMap<>();

    public static void register(AbstractMessageHandler messageHandler) {
        messageHandlerMap.put(messageHandler.getMessageTypeEnum().getType(), messageHandler);
    }

    public static AbstractMessageHandler getMessageHandler(Integer type) {
        return messageHandlerMap.get(type);
    }
}