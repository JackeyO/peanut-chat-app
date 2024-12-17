package com.sici.chat.service.impl;

import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.TextMessageDto;
import com.sici.chat.service.ChatService;
import com.sici.common.enums.chat.message.MessageReqTypeEnum;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import com.sici.common.result.ResponseResult;
import com.sici.framework.redis.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceImplTest {

    @Resource
    private ChatService chatService;
    public MessageRequestDto generateTextMessageReq(Integer userId, Integer roomId) {
        MessageRequestDto messageRequestDto = new MessageRequestDto();
        messageRequestDto.setType(MessageRespTypeEnum.TEXT.getType());
        messageRequestDto.setRoomId(roomId);

        TextMessageDto textMessageDto = new TextMessageDto("hello");
        textMessageDto.setType(MessageRespTypeEnum.TEXT.getType());
        textMessageDto.setRoomId(roomId);
        textMessageDto.setFromUid(userId);
        textMessageDto.setSendTime(new Date());
        textMessageDto.setUpdateTime(new Date());

        messageRequestDto.setBody(textMessageDto);
        return messageRequestDto;
    }

    @Test
    void send() {
        MessageRequestDto messageRequestDto = generateTextMessageReq(1, 11);
//        ResponseResult result = chatService.send(messageRequestDto);
    }
}