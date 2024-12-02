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
import java.util.List;

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
    public TextMessageVo aggregateAll(Message messageMeta, List<Integer> receiverIds) {
        TextMessageVo textMessageVo = new TextMessageVo();
        // 聚合消息META
        textMessageVo.setMessage(super.aggregateMessageMetaInfo(messageMeta));
        // 聚合消息标记信息
        textMessageVo.setMessageMarkVo(super.aggregateMessageMarkInfo(messageMeta, receiverIds));
        // 聚合消息文本内容
        textMessageVo.setContent(textMessageDao.getContentById(messageMeta.getId()));
        return textMessageVo;
    }
}
