package com.sici.chat.handler.msg;

import com.sici.chat.dao.FileMessageDao;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.VideoMessageDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.chat.message.vo.VideoMessageVo;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;


/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 视频消息处理器
 * @create-date: 11/27/2024 5:36 PM
 * @version: 1.0
 */

@Component
public class VideoMessageHandler extends AbstractMessageHandler<VideoMessageDto> {
    @Resource
    private FileMessageDao fileMessageDao;

    @Override
    public MessageRespTypeEnum getMessageTypeEnum() {
        return MessageRespTypeEnum.VIDEO;
    }

    @Override
    public VideoMessageDto getBody(MessageRequestDto messageReq) {
        VideoMessageDto soundMessageDto = (VideoMessageDto) messageReq.getBody();
        return soundMessageDto;
    }

    @Override
    public void extCheck(VideoMessageDto videoMessageDto) {

    }

    @Override
    public VideoMessageVo extSave(Message message, VideoMessageDto videoMessageDto) {
        VideoMessageVo videoMessageVo = new VideoMessageVo();
        videoMessageVo.setMessage(ConvertBeanUtil.convertSingle(message, MessageVo.class));
        // TODO 上传到oss，插入file message记录,回填到videoMessageVo - Su Xiao Wen - 5/26/25 16:43

        return videoMessageVo;
    }
}
