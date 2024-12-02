package com.sici.chat.aggregator;

import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.MessageMarkVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.im.bo.ImMsg;
import com.sici.utils.bean.ConvertBeanUtil;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.aggregator
 * @author: 20148
 * @description: 针对聊天信息的信息聚合器
 * @create-date: 12/2/2024 4:18 PM
 * @version: 1.0
 */

public abstract class ChatMessageAggregator<OUT extends ChatMessageVo> extends AbstractMessageAggregator<OUT> {
    /**
     * 聚合消息标记信息
     * @return
     */
    public MessageMarkVo aggregateMessageMarkInfo(Message message, List<Integer> receiverIds) {
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