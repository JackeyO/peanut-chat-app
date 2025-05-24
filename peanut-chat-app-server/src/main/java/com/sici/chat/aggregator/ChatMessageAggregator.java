package com.sici.chat.aggregator;

import cn.hutool.core.lang.Pair;
import com.sici.chat.dao.MessageMarkDao;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.MessageMarkVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.enums.chat.message.MessageMarkActionEnum;
import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.aggregator
 * @author: 20148
 * @description: 针对聊天信息的信息聚合器
 * @create-date: 12/2/2024 4:18 PM
 * @version: 1.0
 */

public abstract class ChatMessageAggregator<OUT extends ChatMessageVo> extends AbstractMessageAggregator<Message, OUT> {
    /**
     * 由子类根据消息meta信息和标记信息填充子类额外内容，然后返回子类定义的返回体
     * @param message
     * @param messageMarkVo
     * @return
     */
    public abstract OUT fillInfo(MessageVo message, MessageMarkVo messageMarkVo);
    public abstract OUT fillInfoRelationToReceiver(MessageVo message, MessageMarkVo messageMarkVo, Integer receiver);
    public abstract Map<Long, OUT> fillInfoRelationToReceiver(MessageVo messageVo, Map<Long, MessageMarkVo> messageMarkVo, List<Long> receiver);
    @Resource
    private MessageMarkDao messageMarkDao;

    @Override
    public OUT aggregateAll(Message messageMeta) {
        OUT out = fillInfo(aggregateMessageMetaInfo(messageMeta), aggregateMessageMarkInfo(messageMeta));
        return out;
    }

    public OUT aggregateAllRelationToReceiver(Message messageMeta, Integer receiver) {
        OUT out = fillInfoRelationToReceiver(aggregateMessageMetaInfo(messageMeta),
                aggregateMessageMarkInfoRelationToReceiver(messageMeta, receiver),
                receiver);
        return out;
    }
    public Map<Long, OUT> aggregateAllRelationToReceiver(Message messageMeta, List<Long> receiverIds) {
        Map<Long, OUT> out = fillInfoRelationToReceiver(aggregateMessageMetaInfo(messageMeta),
                aggregateMessageMarkInfoRelationToReceiver(messageMeta, receiverIds),
                receiverIds);
        return out;
    }

    /**
     * 聚合消息标记信息
     * @return
     */
    public MessageMarkVo aggregateMessageMarkInfo(Message messageMeta) {
        MessageMarkVo messageMarkVo = new MessageMarkVo();
        Pair<Long, Long> likeAndDislikeCount = messageMarkDao.getLikeAndDislikeCount(messageMeta.getId());
        messageMarkVo.setLikes(likeAndDislikeCount.getKey());
        messageMarkVo.setDisLikes(likeAndDislikeCount.getValue());
        return messageMarkVo;
    }

    /**
     * 聚合消息标记信息(包括接收者对消息的标记行为)
     * @param messageMeta
     * @param receiverId
     * @return
     */
    public MessageMarkVo aggregateMessageMarkInfoRelationToReceiver(Message messageMeta, Integer receiverId) {
        // 获取消息的喜欢和不喜欢数量以及人员id
        Pair<Long, Long> likeAndDislikeCount = messageMarkDao.getLikeAndDislikeCount(messageMeta.getId());
        List<Long> likeUser = messageMarkDao.getLikeUser(messageMeta.getId());
        List<Long> disLikeUser = messageMarkDao.getDisLikeUser(messageMeta.getId());

        MessageMarkVo messageMarkVo = new MessageMarkVo();
        messageMarkVo.setLikes(likeAndDislikeCount.getKey());
        messageMarkVo.setDisLikes(likeAndDislikeCount.getValue());
        messageMarkVo.setCurrentUserLike(likeUser.contains(receiverId) ? MessageMarkActionEnum.YES.getCode() : MessageMarkActionEnum.NO.getCode());
        messageMarkVo.setCurrentUserDisLike(disLikeUser.contains(receiverId) ? MessageMarkActionEnum.YES.getCode() : MessageMarkActionEnum.NO.getCode());
        return messageMarkVo;
    }
    /**
     * 聚合消息标记信息(包括接收者对消息的标记行为)
     * @param messageMeta
     * @param receiverIds
     * @return
     */
    public Map<Long, MessageMarkVo> aggregateMessageMarkInfoRelationToReceiver(Message messageMeta, List<Long> receiverIds) {
        // 获取消息的喜欢和不喜欢数量以及人员id
        Pair<Long, Long> likeAndDislikeCount = messageMarkDao.getLikeAndDislikeCount(messageMeta.getId());
        List<Long> likeUser = messageMarkDao.getLikeUser(messageMeta.getId());
        List<Long> disLikeUser = messageMarkDao.getDisLikeUser(messageMeta.getId());

        HashMap<Long, MessageMarkVo> messageMarkVoMap = new HashMap<>();
        receiverIds.forEach(receiverId -> {
            MessageMarkVo messageMarkVo = new MessageMarkVo();
            messageMarkVo.setLikes(likeAndDislikeCount.getKey());
            messageMarkVo.setDisLikes(likeAndDislikeCount.getValue());
            messageMarkVo.setCurrentUserLike(likeUser.contains(receiverId) ? MessageMarkActionEnum.YES.getCode() : MessageMarkActionEnum.NO.getCode());
            messageMarkVo.setCurrentUserDisLike(disLikeUser.contains(receiverId) ? MessageMarkActionEnum.YES.getCode() : MessageMarkActionEnum.NO.getCode());
            messageMarkVoMap.put(receiverId, messageMarkVo);
        });
        return messageMarkVoMap;
    }

    /**
     * 聚合消息meta信息
     * @param messageMeta
     * @return
     */
    public MessageVo aggregateMessageMetaInfo(Message messageMeta) {
        return ConvertBeanUtil.convertSingle(messageMeta, MessageVo.class);
    }
}