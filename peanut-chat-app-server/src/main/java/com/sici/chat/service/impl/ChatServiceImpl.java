package com.sici.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.asser.AssertUtil;
import com.sici.chat.dao.MessageDao;
import com.sici.chat.event.MessageSendEvent;
import com.sici.chat.handler.msg.AbstractMessageHandler;
import com.sici.chat.handler.msg.MessageHandlerFactory;
import com.sici.chat.model.chat.cursor.dto.CursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.service.ChatService;
import com.sici.common.result.ResponseResult;
import com.sici.utils.bean.ConvertBeanUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.service.impl
 * @author: 20148
 * @description: 聊天业务实现
 * @create-date: 11/24/2024 3:36 PM
 * @version: 1.0
 */

@Service
public class ChatServiceImpl implements ChatService {
    /**
     * spring 应用事件发布器
     */
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageViewAdapter messageViewAdapter;

    @Override
    public ResponseResult send(MessageRequestDto messageRequestDto) {
        // 根据消息类型获取对应的消息处理器并处理消息(包括检查并保存消息)
        AbstractMessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(messageRequestDto.getType());
        Message message = messageHandler.checkAndSave(messageRequestDto);
        // 发布消息发送事件
        applicationEventPublisher.publishEvent(new MessageSendEvent(this, message.getId()));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult messagePage(CursorPageDto cursorPageDto) {
        AssertUtil.isNotEmpty(cursorPageDto, "分页对象不能为空");
        CursorPageVo<Message> messagePageByCursor = messageDao.getMessagePageByCursor(cursorPageDto);

        CursorPageVo<ChatMessageVo> chatMessageVoCursorPageVo = new CursorPageVo<>();
        BeanUtil.copyProperties(messagePageByCursor, chatMessageVoCursorPageVo, "records");

        chatMessageVoCursorPageVo.setRecords(messagePageByCursor.getRecords().stream()
                .map(message -> messageViewAdapter.adaptChatMessage(message))
                .collect(Collectors.toList()));
        return ResponseResult.okResult(chatMessageVoCursorPageVo);
    }
}