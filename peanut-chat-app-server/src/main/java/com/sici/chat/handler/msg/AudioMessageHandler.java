package com.sici.chat.handler.msg;

import com.sici.chat.dao.FileMessageDao;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.AudioMessageDto;
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
        SoundMessageVo soundMessageVo = new SoundMessageVo();
        soundMessageVo.setMessage(ConvertBeanUtil.convertSingle(message, MessageVo.class));

        // TODO 上传到oss，插入file message记录,回填到soundMessageVo - Su Xiao Wen - 5/26/25 16:43

        return soundMessageVo;
    }
}
