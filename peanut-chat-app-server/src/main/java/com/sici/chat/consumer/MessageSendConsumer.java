package com.sici.chat.consumer;

import java.util.List;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.builder.ImMsgBuilder;
import com.sici.chat.cache.GroupRoomMemberCache;
import com.sici.chat.cache.RoomCache;
import com.sici.chat.cache.TwoPersonRoomCache;
import com.sici.chat.dao.MessageDao;
import com.sici.chat.model.chat.message.dto.MessageSendDTO;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.service.ws.PushService;
import com.sici.common.constant.im.ChatMqConstant;
import com.sici.common.enums.chat.room.RoomTypeEnums;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.consumer
 * @author: 20148
 * @description: 消息发送消费者，负责更新相关缓存以及利用MQ发送消息推送
 * @create-date: 11/30/2024 3:57 PM
 * @version: 1.0
 */

@RocketMQMessageListener(topic = ChatMqConstant.SEND_MSG_TOPIC, consumerGroup = ChatMqConstant.SEND_MSG_GROUP)
@Component
public class MessageSendConsumer implements RocketMQListener<MessageSendDTO> {
    @Resource
    private PushService pushService;
    @Resource
    private MessageDao messageDao;
    @Resource
    private GroupRoomMemberCache groupRoomMemberCache;
    @Resource
    private TwoPersonRoomCache twoPersonRoomCache;
    @Resource
    private RoomCache roomCache;
    @Resource
    private MessageViewAdapter messageViewAdapter;

    @Override
    public void onMessage(MessageSendDTO messageSendDTO) {
        Integer msgId = messageSendDTO.getMsgId();
        // 获取消息META信息
        Message message = messageDao.getById(msgId);

        // 获取房间信息
        Integer roomId = message.getRoomId();
        Room room = roomCache.getOne(roomId);

        // 获取房间成员列表
        List<Integer> roomMemberIds = room.getType().equals(RoomTypeEnums.TWO_PRIVATE) ?
                twoPersonRoomCache.getOne(roomId).getMembers() : groupRoomMemberCache.getOne(roomId).getMembers();

        // 构建ImMsg,准备进行消息推送
        ImMsg<ChatMessageVo> imMsg = ImMsgBuilder.buildChatMessage(messageViewAdapter.adaptChatMessage(message));

        // 执行消息推送
        pushService.pushMsg(imMsg, roomMemberIds);
    }
}