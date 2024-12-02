package com.sici.chat.aggregator;

import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.CommonMessageVo;
import com.sici.common.enums.chat.message.MessageTypeEnum;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.aggregator
 * @author: 20148
 * @description: 消息各类信息聚合器(根据消息的meta信息，将将消息的各种详细信息聚合到一个对象中返回)
 * @create-date: 12/2/2024 4:03 PM
 * @version: 1.0
 */

public abstract class AbstractMessageAggregator<OUT extends CommonMessageVo> {
    public abstract MessageTypeEnum getSupportedMessageEnum();
    public abstract OUT aggregateAll(Message messageMeta, List<Integer> receiverIds);

    public AbstractMessageAggregator() {
        MessageAggregatorFactory.register(this);
    }
}