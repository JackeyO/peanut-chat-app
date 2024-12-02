package com.sici.chat.event.listener;

import com.sici.chat.event.MessageSendEvent;
import com.sici.chat.model.chat.message.dto.MessageSendDTO;
import com.sici.common.constant.message.MessageMqConstant;
import com.sici.qiyu.live.framework.rmq.config.MQProducer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.event.listener
 * @author: 20148
 * @description: 消息发送事件监听器
 * @create-date: 11/28/2024 4:15 PM
 * @version: 1.0
 */

@Component
public class MessageSendEventListener {
    @Resource
    private MQProducer mqProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void messageRoute(MessageSendEvent event) {
        Integer msgId = event.getMsgId();
        mqProducer.sendSecureMsg(MessageMqConstant.SEND_MSG_TOPIC, new MessageSendDTO(msgId), msgId);
    }
}