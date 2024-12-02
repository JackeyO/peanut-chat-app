package com.sici.chat.aggregator;

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

public class MessageAggregatorFactory {
    public static Map<Integer, AbstractMessageAggregator> messageAggregatorMap = new HashMap<>();

    public static void register(AbstractMessageAggregator messageAggregator) {
        messageAggregatorMap.put(messageAggregator.getSupportedMessageEnum().getType(), messageAggregator);
    }

    public static AbstractMessageAggregator getMessageAggregator(Integer type) {
        return messageAggregatorMap.get(type);
    }
}