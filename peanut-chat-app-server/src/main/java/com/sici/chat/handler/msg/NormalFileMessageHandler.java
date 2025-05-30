package com.sici.chat.handler.msg;

import com.sici.chat.dao.FileMessageDao;
import com.sici.chat.model.chat.message.dto.BaseFileMessageDto;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.entity.FileMessage;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.chat.message.vo.NormalFileMessageVo;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;


/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 普通文件消息处理器
 * @create-date: 11/27/2024 5:36 PM
 * @version: 1.0
 */

@Component
public class NormalFileMessageHandler extends AbstractMessageHandler<BaseFileMessageDto> {
    @Resource
    private FileMessageDao fileMessageDao;

    @Override
    public MessageRespTypeEnum getMessageTypeEnum() {
        return MessageRespTypeEnum.NORMAL_FILE;
    }

    @Override
    public BaseFileMessageDto getBody(MessageRequestDto messageReq) {
        BaseFileMessageDto baseFileMessageDto = (BaseFileMessageDto) messageReq.getBody();
        return baseFileMessageDto;
    }

    @Override
    public void extCheck(BaseFileMessageDto baseFileMessageDto) {

    }

    @Override
    public NormalFileMessageVo extSave(Message message, BaseFileMessageDto baseFileMessageDto) {
        // 插入file message记录
        FileMessage fileMessage = FileMessage.builder()
                .msgId(message.getId())
                .url(baseFileMessageDto.getUrl())
                .size(baseFileMessageDto.getSize())
                .type(MessageRespTypeEnum.NORMAL_FILE.getType())
                .build();
        fileMessageDao.save(fileMessage);


        NormalFileMessageVo normalFileMessageVo = new NormalFileMessageVo();
        normalFileMessageVo.setMessage(ConvertBeanUtil.convertSingle(message, MessageVo.class));
        normalFileMessageVo.setUrl(baseFileMessageDto.getUrl());
        normalFileMessageVo.setType(baseFileMessageDto.getFileType());
        normalFileMessageVo.setSize(baseFileMessageDto.getSize());

        return normalFileMessageVo;
    }
}
