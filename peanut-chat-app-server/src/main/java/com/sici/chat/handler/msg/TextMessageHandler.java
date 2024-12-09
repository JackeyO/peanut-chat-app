package com.sici.chat.handler.msg;

import com.sici.chat.asser.AssertUtil;
import com.sici.chat.dao.TextMessageDao;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.TextMessageDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.entity.TextMessage;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 文本新消息处理器
 * @create-date: 11/27/2024 5:24 PM
 * @version: 1.0
 */

@Component
public class TextMessageHandler extends AbstractMessageHandler<TextMessageDto> {
    @Resource
    private TextMessageDao textMessageDao;
    @Override
    public MessageRespTypeEnum getMessageTypeEnum() {
        return MessageRespTypeEnum.TEXT;
    }

    @Override
    public TextMessageDto getBody(MessageRequestDto messageReq) {
        AssertUtil.isTrue(super.support(messageReq), "消息类型不是文本消息, messageReq: " + messageReq);
        TextMessageDto textMessageDto = (TextMessageDto) messageReq.getBody();
        return textMessageDto;
    }

    @Override
    public void extCheck(MessageRequestDto messageReq) {
        // TODO: 检查文本消息的内容是否有敏感词  || created by 20148 at 11/27/2024 6:28 PM
        AssertUtil.isTrue(super.support(messageReq), "消息类型不符合文本消息, messageReq: " + messageReq);
        TextMessageDto body = (TextMessageDto) messageReq.getBody();
    }

    @Override
    public void extSave(Message message, MessageRequestDto messageReq) {
        TextMessageDto textMessageDto = getBody(messageReq);

        // 保存文本消息的内容
        textMessageDao.save(TextMessage.builder()
                .msgId(message.getId())
                .content(textMessageDto.getContent())
                .build());
    }
}
