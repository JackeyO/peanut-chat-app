package com.sici.chat.aggregator;

import com.sici.chat.dao.TextMessageDao;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.MessageMarkVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.chat.message.vo.TextMessageVo;
import com.sici.common.enums.chat.message.MessageTypeEnum;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.aggregator
 * @author: 20148
 * @description: 文本消息聚合
 * @create-date: 12/2/2024 4:05 PM
 * @version: 1.0
 */

@Component
public class TextMessageAggregator extends ChatMessageAggregator<TextMessageVo> {
    @Resource
    private TextMessageDao textMessageDao;
    @Override
    public MessageTypeEnum getSupportedMessageEnum() {
        return MessageTypeEnum.TEXT;
    }

    @Override
    public TextMessageVo aggregateAll(Message messageMeta) {
        TextMessageVo textMessageVo = new TextMessageVo();
        // 聚合消息META
        textMessageVo.setMessage(super.aggregateMessageMetaInfo(messageMeta));
        // 聚合消息标记信息
        textMessageVo.setMessageMarkVo(super.aggregateMessageMarkInfo(messageMeta));
        // 聚合消息文本内容
        textMessageVo.setContent(textMessageDao.getContentById(messageMeta.getId()));
        return textMessageVo;
    }
    @Override
    public TextMessageVo aggregateAllRelationToReceiver(Message messageMeta, Integer receiver) {
        TextMessageVo textMessageVo = new TextMessageVo();
        // 聚合消息META
        textMessageVo.setMessage(super.aggregateMessageMetaInfo(messageMeta));
        // 聚合消息标记信息
        textMessageVo.setMessageMarkVo(super.aggregateMessageMarkInfoWithCurrentUserAction(messageMeta, receiver));
        // 聚合消息文本内容
        textMessageVo.setContent(textMessageDao.getContentById(messageMeta.getId()));
        return textMessageVo;
    }
    @Override
    public Map<Integer, TextMessageVo> aggregateAllRelationToReceiver(Message messageMeta, List<Integer> receiverIds) {
        HashMap<Integer, TextMessageVo> textMessageVoMap = new HashMap<>();
        Map<Integer, MessageMarkVo> messageMarkVoMap = super.aggregateMessageMarkInfoWithCurrentUserAction(messageMeta, receiverIds);
        receiverIds.forEach(receiverId -> {
            TextMessageVo textMessageVo = new TextMessageVo();
            // 聚合消息META
            textMessageVo.setMessage(super.aggregateMessageMetaInfo(messageMeta));
            // 聚合消息标记信息
            textMessageVo.setMessageMarkVo(messageMarkVoMap.get(receiverId));
            // 聚合消息文本内容
            textMessageVo.setContent(textMessageDao.getContentById(messageMeta.getId()));
            textMessageVoMap.put(receiverId, textMessageVo);
        });
        return textMessageVoMap;
    }
}
