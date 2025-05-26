package com.sici.chat.handler.msg;

import com.sici.chat.dao.MessageDao;
import com.sici.chat.model.chat.message.dto.MessageDto;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.util.AssertUtil;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;


/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 文本新消息处理器
 * @create-date: 11/27/2024 5:24 PM
 * @version: 1.0
 */

@Component
public class TextMessageHandler extends AbstractMessageHandler<MessageDto> {
    @Resource
    private MessageDao messageDao;
    @Override
    public MessageRespTypeEnum getMessageTypeEnum() {
        return MessageRespTypeEnum.TEXT;
    }

    @Override
    public MessageDto getBody(MessageRequestDto messageReq) {
        MessageDto MessageDto = (MessageDto) messageReq.getBody();
        return MessageDto;
    }

    @Override
    public void extCheck(MessageDto messageDto) {
        // TODO: 检查文本消息的内容是否有敏感词  || created by 20148 at 11/27/2024 6:28 PM
    }

    @Override
    public ChatMessageVo extSave(Message message, MessageDto body) {
        ChatMessageVo chatMessageVo = new ChatMessageVo();
        chatMessageVo.setMessage(ConvertBeanUtil.convertSingle(message, MessageVo.class));
        return chatMessageVo;
    }
}
