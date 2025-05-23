package com.sici.chat.dao;

import java.util.List;
import java.util.stream.Collectors;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.model.chat.cursor.dto.MessageCursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;

import cn.hutool.core.bean.BeanUtil;

@SpringBootTest
@Slf4j
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

    @Test
    void testSaveMessage() {
        Message message = new Message();
        message.setRoomId(1L);
        message.setFromUid(1L);
        message.setType(0);
        boolean save = messageDao.save(message);
        log.info(save ? "<UNK>" : "<UNK>");
    }
}