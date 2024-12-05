package com.sici.chat.adapter;

import com.sici.chat.aggregator.MessageAggregatorFactory;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.CommonMessageVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.adapter
 * @author: 20148
 * @description: 消息展示适配器(用于生成针对各类消息的展示物料)
 * @create-date: 12/2/2024 3:49 PM
 * @version: 1.0
 */

@Component
public class MessageViewAdapter {
    /**
     * 适配消息信息与接收者无关
     * @param message
     * @return
     */
    public CommonMessageVo doAdapt(Message message) {
        return MessageAggregatorFactory.getMessageAggregator(message.getType())
                .aggregateAll(message);
    }

    /**
     * 需要根据接收者来适配消息信息
     * @param message
     * @param receiverIds
     * @return
     */
    public CommonMessageVo doAdaptRelationToReceiver(Message message, Integer receiverIds) {
        return MessageAggregatorFactory.getMessageAggregator(message.getType())
                .aggregateAllRelationToReceiver(message, receiverIds);
    }
    /**
     * 需要根据接收者来适配消息信息
     * @param message
     * @param receiverIds
     * @return
     */
    public Map<Integer, CommonMessageVo> doAdaptRelationToReceiver(Message message, List<Integer> receiverIds) {
        return MessageAggregatorFactory.getMessageAggregator(message.getType())
                .aggregateAllRelationToReceiver(message, receiverIds);
    }
}