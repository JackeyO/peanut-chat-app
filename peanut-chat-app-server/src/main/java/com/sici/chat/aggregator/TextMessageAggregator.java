package com.sici.chat.aggregator;

import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.MessageMarkVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import org.springframework.stereotype.Component;

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
public class TextMessageAggregator extends ChatMessageAggregator<ChatMessageVo> {
    @Override
    public MessageRespTypeEnum getSupportedMessageEnum() {
        return MessageRespTypeEnum.TEXT;
    }

    @Override
    public ChatMessageVo fillInfo(MessageVo messageVo, MessageMarkVo messageMarkVo) {
        ChatMessageVo textMessageVo = new ChatMessageVo();
        // 聚合消息META
        textMessageVo.setMessage(messageVo);
        // 聚合消息标记信息
        textMessageVo.setMessageMarkVo(messageMarkVo);
        return textMessageVo;
    }


    @Override
    public ChatMessageVo fillInfoRelationToReceiver(MessageVo messageVo, MessageMarkVo messageMarkVo, Integer receiver) {
        return fillInfo(messageVo, messageMarkVo);
    }

    @Override
    public Map<Long, ChatMessageVo> fillInfoRelationToReceiver(MessageVo messageVo, Map<Long, MessageMarkVo> messageMarkVo, List<Long> receivers) {
        HashMap<Long, ChatMessageVo> out = new HashMap<>();
        receivers.forEach(receiver -> {
            out.put(receiver, fillInfo(messageVo, messageMarkVo.get(receiver)));
        });
        return out;
    }
}
