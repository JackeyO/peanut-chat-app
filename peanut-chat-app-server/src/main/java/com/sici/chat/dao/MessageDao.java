package com.sici.chat.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.mapper.MessageMapper;
import com.sici.chat.model.chat.cursor.dto.MessageCursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.util.CursorPageUtil;

/**
 * @author 20148
 * @description 针对表【message】的数据库操作Service实现
 * @createDate 2024-11-26 17:27:36
 */
@Component
public class MessageDao extends ServiceImpl<MessageMapper, Message> {
    @Resource
    private MessageViewAdapter messageViewAdapter;

    public CursorPageVo<Message> getMessagePageByCursor(MessageCursorPageDto messageCursorPageDto) {
        CursorPageVo<Message> messagePageByCursor = CursorPageUtil.getCursorPageOfMySql(this, messageCursorPageDto,
                (wrapper, cursorValue) -> {
                    if (cursorValue != null) {
                        wrapper.lt(Message::getSendTime, cursorValue);
                    }
                    wrapper.orderByDesc(Message::getSendTime);
                    wrapper.eq(Message::getRoomId, messageCursorPageDto.getRoomId());
                },
                Message::getSendTime);
        return messagePageByCursor;
    }
}




