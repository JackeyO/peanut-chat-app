package com.sici.chat.aggregator;

import cn.hutool.core.lang.Pair;
import com.sici.chat.dao.MessageMarkDao;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.MessageMarkVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.common.enums.chat.message.MessageMarkActionEnums;
import com.sici.utils.bean.ConvertBeanUtil;

import javax.annotation.Resource;
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

public abstract class ChatMessageAggregator<OUT extends ChatMessageVo> extends AbstractMessageAggregator<OUT> {
    @Resource
    private MessageMarkDao messageMarkDao;

    /**
     * 聚合消息标记信息
     * @return
     */
    public MessageMarkVo aggregateMessageMarkInfo(Message message) {
        MessageMarkVo messageMarkVo = new MessageMarkVo();
        Pair<Long, Long> likeAndDislikeCount = messageMarkDao.getLikeAndDislikeCount(message.getId());
        messageMarkVo.setLikes(likeAndDislikeCount.getKey());
        messageMarkVo.setDisLikes(likeAndDislikeCount.getValue());
        return messageMarkVo;
    }

    /**
     * 聚合消息标记信息(包括接收者对消息的标记行为)
     * @param message
     * @param receiverId
     * @return
     */
    public MessageMarkVo aggregateMessageMarkInfoWithCurrentUserAction(Message message, Integer receiverId) {
        // 获取消息的喜欢和不喜欢数量以及人员id
        Pair<Long, Long> likeAndDislikeCount = messageMarkDao.getLikeAndDislikeCount(message.getId());
        List<Integer> likeUser = messageMarkDao.getLikeUser(message.getId());
        List<Integer> disLikeUser = messageMarkDao.getDisLikeUser(message.getId());

        MessageMarkVo messageMarkVo = new MessageMarkVo();
        messageMarkVo.setLikes(likeAndDislikeCount.getKey());
        messageMarkVo.setDisLikes(likeAndDislikeCount.getValue());
        messageMarkVo.setCurrentUserLike(likeUser.contains(receiverId) ? MessageMarkActionEnums.YES.getCode() : MessageMarkActionEnums.NO.getCode());
        messageMarkVo.setCurrentUserDisLike(disLikeUser.contains(receiverId) ? MessageMarkActionEnums.YES.getCode() : MessageMarkActionEnums.NO.getCode());
        return messageMarkVo;
    }
    /**
     * 聚合消息标记信息(包括接收者对消息的标记行为)
     * @param message
     * @param receiverIds
     * @return
     */
    public Map<Integer, MessageMarkVo> aggregateMessageMarkInfoWithCurrentUserAction(Message message, List<Integer> receiverIds) {
        // 获取消息的喜欢和不喜欢数量以及人员id
        Pair<Long, Long> likeAndDislikeCount = messageMarkDao.getLikeAndDislikeCount(message.getId());
        List<Integer> likeUser = messageMarkDao.getLikeUser(message.getId());
        List<Integer> disLikeUser = messageMarkDao.getDisLikeUser(message.getId());

        HashMap<Integer, MessageMarkVo> messageMarkVoMap = new HashMap<>();
        receiverIds.forEach(receiverId -> {
            MessageMarkVo messageMarkVo = new MessageMarkVo();
            messageMarkVo.setLikes(likeAndDislikeCount.getKey());
            messageMarkVo.setDisLikes(likeAndDislikeCount.getValue());
            messageMarkVo.setCurrentUserLike(likeUser.contains(receiverId) ? MessageMarkActionEnums.YES.getCode() : MessageMarkActionEnums.NO.getCode());
            messageMarkVo.setCurrentUserDisLike(disLikeUser.contains(receiverId) ? MessageMarkActionEnums.YES.getCode() : MessageMarkActionEnums.NO.getCode());
            messageMarkVoMap.put(receiverId, messageMarkVo);
        });
        return messageMarkVoMap;
    }

    /**
     * 聚合消息meta信息
     * @param message
     * @return
     */
    public MessageVo aggregateMessageMetaInfo(Message message) {
        return ConvertBeanUtil.convertSingle(message, MessageVo.class);
    }
}