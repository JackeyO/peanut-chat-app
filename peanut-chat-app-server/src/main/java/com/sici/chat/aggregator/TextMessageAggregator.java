package com.sici.chat.aggregator;

import com.sici.chat.dao.TextMessageDao;
import com.sici.chat.model.chat.message.vo.MessageMarkVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.chat.message.vo.TextMessageVo;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
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
    public MessageRespTypeEnum getSupportedMessageEnum() {
        return MessageRespTypeEnum.TEXT;
    }

    @Override
    public TextMessageVo fillInfo(MessageVo messageVo, MessageMarkVo messageMarkVo) {
        TextMessageVo textMessageVo = new TextMessageVo();
        // 聚合消息META
        textMessageVo.setMessage(messageVo);
        // 聚合消息标记信息
        textMessageVo.setMessageMarkVo(messageMarkVo);
        // 聚合消息文本内容
        textMessageVo.setContent(textMessageDao.getContentById(messageVo.getId()));
        return textMessageVo;
    }


    @Override
    public TextMessageVo fillInfoRelationToReceiver(MessageVo messageVo, MessageMarkVo messageMarkVo, Integer receiver) {
        return fillInfo(messageVo, messageMarkVo);
    }

    @Override
    public Map<Integer, TextMessageVo> fillInfoRelationToReceiver(MessageVo messageVo, Map<Integer, MessageMarkVo> messageMarkVo, List<Integer> receivers) {
        HashMap<Integer, TextMessageVo> out = new HashMap<>();
        receivers.forEach(receiver -> {
            out.put(receiver, fillInfo(messageVo, messageMarkVo.get(receiver)));
        });
        return out;
    }
}
