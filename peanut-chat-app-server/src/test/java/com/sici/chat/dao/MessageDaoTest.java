package com.sici.chat.dao;

import cn.hutool.core.bean.BeanUtil;
import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.model.chat.cursor.dto.CursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageDaoTest {
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageViewAdapter messageViewAdapter;
    @Test
    void getMessagePageByCursor() {
        CursorPageDto cursorPageDto = new CursorPageDto();
        CursorPageVo<Message> messagePageByCursor = messageDao.getMessagePageByCursor(cursorPageDto);
        CursorPageVo<ChatMessageVo> chatMessageVos = new CursorPageVo<>();
        BeanUtil.copyProperties(messagePageByCursor, chatMessageVos, "records");

        List<ChatMessageVo> newRecords = messagePageByCursor.getRecords().stream()
                .map(message -> messageViewAdapter.adaptChatMessage(message))
                .collect(Collectors.toList());
        chatMessageVos.setRecords(newRecords);
    }
}