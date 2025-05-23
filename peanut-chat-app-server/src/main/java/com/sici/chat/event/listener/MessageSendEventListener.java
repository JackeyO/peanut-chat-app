package com.sici.chat.event.listener;

import com.sici.chat.event.MessageSendEvent;
import com.sici.chat.model.chat.message.dto.MessageSendDTO;
import com.sici.common.constant.im.ChatMqConstant;
import com.sici.chat.framework.rmq.config.MQProducer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


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
        Long msgId = event.getMsgId();
        mqProducer.sendSecureMsg(ChatMqConstant.SEND_MSG_TOPIC, new MessageSendDTO(msgId), msgId);
    }
}