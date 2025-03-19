package com.sici.chat.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.model.chat.cursor.dto.MessageCursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;

import cn.hutool.core.bean.BeanUtil;

@SpringBootTest
class MessageDaoTest {
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageViewAdapter messageViewAdapter;
    @Test
    void getMessagePageByCursor() {
        MessageCursorPageDto messageCursorPageDto = new MessageCursorPageDto();
        messageCursorPageDto.setRoomId(1);
        CursorPageVo<Message> messagePageByCursor = messageDao.getMessagePageByCursor(messageCursorPageDto);
        CursorPageVo<ChatMessageVo> chatMessageVos = new CursorPageVo<>();
        BeanUtil.copyProperties(messagePageByCursor, chatMessageVos, "records");

        List<ChatMessageVo> newRecords = messagePageByCursor.getRecords().stream()
                .map(message -> messageViewAdapter.adaptChatMessage(message))
                .collect(Collectors.toList());
        chatMessageVos.setRecords(newRecords);
    }
}