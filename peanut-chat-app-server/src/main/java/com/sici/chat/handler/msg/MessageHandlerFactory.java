package com.sici.chat.handler.msg;

import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.entity.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 消息处理器工厂类
 * @create-date: 11/25/2024 5:31 PM
 * @version: 1.0
 */

@Slf4j
public class MessageHandlerFactory {
    public static Map<Integer, AbstractMessageHandler> messageHandlerMap = new HashMap<>();

    public static void register(AbstractMessageHandler messageHandler) {
        messageHandlerMap.put(messageHandler.getMessageTypeEnum().getType(), messageHandler);
    }

    public static AbstractMessageHandler getMessageHandler(Integer type) {
        return messageHandlerMap.get(type);
    }

    public static Message handleMessage(MessageRequestDto messageRequestDto) {
        if (Objects.isNull(messageRequestDto)) {
            return null;
        }
        AbstractMessageHandler messageHandler = getMessageHandler(messageRequestDto.getType());
        if (Objects.nonNull(messageHandler)) {
            return messageHandler.checkAndSave(messageRequestDto);
        }
        else {
            log.error("No message handler found for type: {}, messageRequestDto:{}", messageRequestDto.getType(), messageRequestDto);
            return null;
        }
    }
}