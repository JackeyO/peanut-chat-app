package com.sici.chat.handler.msg;

import com.sici.chat.dao.FileMessageDao;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.AudioMessageDto;
import com.sici.chat.model.chat.message.entity.FileMessage;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.chat.message.vo.SoundMessageVo;
import com.sici.chat.util.ConvertBeanUtil;
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
public class AudioMessageHandler extends AbstractMessageHandler<AudioMessageDto> {
    @Resource
    private FileMessageDao fileMessageDao;

    @Override
    public MessageRespTypeEnum getMessageTypeEnum() {
        return MessageRespTypeEnum.AUDIO;
    }

    @Override
    public AudioMessageDto getBody(MessageRequestDto messageReq) {
        AudioMessageDto audioMessageDto = (AudioMessageDto) messageReq.getBody();
        return audioMessageDto;
    }

    @Override
    public void extCheck(AudioMessageDto audioMessageDto) {

    }

    @Override
    public SoundMessageVo extSave(Message message, AudioMessageDto audioMessageDto) {
        // 插入file message记录
        FileMessage fileMessage = FileMessage.builder()
                .msgId(message.getId())
                .url(audioMessageDto.getUrl())
                .size(audioMessageDto.getSize())
                .duration(audioMessageDto.getDuration())
                .type(MessageRespTypeEnum.AUDIO.getType())
                .build();
        fileMessageDao.save(fileMessage);

        SoundMessageVo soundMessageVo = new SoundMessageVo();
        soundMessageVo.setMessage(ConvertBeanUtil.convertSingle(message, MessageVo.class));
        soundMessageVo.setType(audioMessageDto.getType());
        soundMessageVo.setUrl(audioMessageDto.getUrl());
        soundMessageVo.setDuration(audioMessageDto.getDuration());
        soundMessageVo.setSize(audioMessageDto.getSize());

        return soundMessageVo;
    }
}
