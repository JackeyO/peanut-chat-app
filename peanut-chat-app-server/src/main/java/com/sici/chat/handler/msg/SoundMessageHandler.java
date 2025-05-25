package com.sici.chat.handler.msg;

import com.sici.chat.dao.FileMessageDao;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.SoundMessageDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.util.AssertUtil;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;


/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 音频消息处理器
 * @create-date: 11/27/2024 5:36 PM
 * @version: 1.0
 */

@Component
public class SoundMessageHandler extends AbstractMessageHandler<SoundMessageDto> {
    @Resource
    private FileMessageDao fileMessageDao;

    @Override
    public MessageRespTypeEnum getMessageTypeEnum() {
        return MessageRespTypeEnum.AUDIO;
    }

    @Override
    public SoundMessageDto getBody(MessageRequestDto messageReq) {
        AssertUtil.isTrue(super.support(messageReq), "消息类型不是音频消息, messageReq:" + messageReq);
        SoundMessageDto soundMessageDto = (SoundMessageDto) messageReq.getBody();
        return soundMessageDto;
    }

    @Override
    public void extCheck(MessageRequestDto messageReq) {

    }

    @Override
    public void extSave(Message message, MessageRequestDto messageReq) {
        SoundMessageDto body = getBody(messageReq);

    }
}
