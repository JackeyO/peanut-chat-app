package com.sici.chat.service.impl.chat;

import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.asser.AssertUtil;
import com.sici.chat.dao.MessageDao;
import com.sici.chat.event.MessageSendEvent;
import com.sici.chat.handler.msg.AbstractMessageHandler;
import com.sici.chat.handler.msg.MessageHandlerFactory;
import com.sici.chat.model.chat.cursor.dto.MessageCursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.UserLikeOrDislikeDto;
import com.sici.chat.model.chat.message.dto.mq.UserDislikeMessageMqDto;
import com.sici.chat.model.chat.message.dto.mq.UserLikeMessageMqDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.service.chat.ChatService;
import com.sici.common.constant.message.MessageMqConstant;
import com.sici.common.result.ResponseResult;
import com.sici.qiyu.live.framework.rmq.config.MQProducer;

import cn.hutool.core.bean.BeanUtil;

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
    @Resource
    private ChatService chatService;
    @Resource
    private MQProducer mqProducer;

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
    public ResponseResult messagePage(MessageCursorPageDto messageCursorPageDto) {
        AssertUtil.isNotEmpty(messageCursorPageDto, "分页对象不能为空");
        CursorPageVo<Message> messagePageByCursor = messageDao.getMessagePageByCursor(messageCursorPageDto);

        CursorPageVo<ChatMessageVo> chatMessageVoCursorPageVo = new CursorPageVo<>();
        BeanUtil.copyProperties(messagePageByCursor, chatMessageVoCursorPageVo, "records");

        chatMessageVoCursorPageVo.setRecords(messagePageByCursor.getRecords().stream()
                .map(message -> messageViewAdapter.adaptChatMessage(message))
                .collect(Collectors.toList()));
        return ResponseResult.okResult(chatMessageVoCursorPageVo);
    }

    @Override
    public void likeOrDislike(UserLikeOrDislikeDto userLikeOrDislikeDto) {
        // 参数校验
        AssertUtil.isNotEmpty(userLikeOrDislikeDto, "点赞或踩对象不能为空");
        AssertUtil.isNotEmpty(userLikeOrDislikeDto.getMessageId(), "消息id不能为空");
        // 根据消息id获取消息
        Message message = messageDao.getById(userLikeOrDislikeDto.getMessageId());
        // 获取消息的点赞或踩状态
        Boolean isLike = userLikeOrDislikeDto.getIsLike();
        Boolean isDislike = userLikeOrDislikeDto.getIsDislike();
        // 如果点赞和踩状态都为true，则抛出异常
        if (isLike && isDislike) {
            throw new RuntimeException("点赞和踩状态不能同时为true");
        }
        // 如果点赞和踩状态都为false，则抛出异常
        if (!isLike && !isDislike) {
            throw new RuntimeException("点赞和踩状态不能同时为false");
        }
    
        // 发送MQ消息(点赞消息或者点踩消息)
        if (isLike) mqProducer.sendMsg(MessageMqConstant.LIKE_MESSAGE_TOPIC, new UserLikeMessageMqDto(message.getId(), message.getFromUid()));
        else mqProducer.sendMsg(MessageMqConstant.DISLIKE_MESSAGE_TOPIC, new UserDislikeMessageMqDto(message.getId(), message.getFromUid()));


        // TODO: 创建消费者，插入用户点赞行为记录表 created by 749291 at 2025-03-19 21:59
    }

}