package com.sici.chat.service.impl;

import com.sici.chat.event.MessageSendEvent;
import com.sici.chat.handler.AbstractMessageHandler;
import com.sici.chat.handler.MessageHandlerFactory;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.service.ChatService;
import com.sici.common.result.ResponseResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.service.impl
 * @author: 20148
 * @description: 聊天业务实现
 * @create-date: 11/24/2024 3:36 PM
 * @version: 1.0
 */

@Service
public class ChatServiceImpl implements ChatService {
    /**
     * spring 应用事件发布器
     */
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ResponseResult send(MessageRequestDto messageRequestDto) {
        // 根据消息类型获取对应的消息处理器并处理消息(包括检查并保存消息)
        AbstractMessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(messageRequestDto.getType());
        Message message = messageHandler.checkAndSave(messageRequestDto);
        // 发布消息发送事件
        applicationEventPublisher.publishEvent(new MessageSendEvent(this, message.getId()));
        return ResponseResult.okResult();
    }
}