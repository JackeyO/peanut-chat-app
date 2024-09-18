package com.sici.live.provider.message.consumer;

import com.alibaba.fastjson.JSON;
import com.sici.live.model.im.dto.ImMsgBody;
import com.sici.live.provider.message.handler.MessageHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.message.consumer
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:30 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class ImMessageReceiveConsumer implements MessageListenerConcurrently {
    @Resource
    private MessageHandler messageHandler;
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        String imMsgBodyStr = new String(msgs.get(0).getBody());
        log.info("[im-message-provider]==>mq==>[IM消息接收-消费者--接收到消息]==>msgStr is {}", imMsgBodyStr);
        if (!StringUtils.isEmpty(imMsgBodyStr)) {
            ImMsgBody imMsgBody = JSON.parseObject(imMsgBodyStr, ImMsgBody.class);
            if (imMsgBody == null || imMsgBody.getUserId() == null || imMsgBody.getAppId() == null) {
                log.error("[im-message-provider]==>mq==>[IM消息接收-消费者--接收到消息]==>消息参数不合法");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            try {
                handle(imMsgBody);
                log.info("[im-message-provider]==>mq==>[IM消息接收-消费者--处理成功], 消息内容:{}", imMsgBody);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("[im-message-provider]==>mq==>[IM消息接收-消费者--处理失败], 消息内容:{}", imMsgBody);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    void handle(ImMsgBody imMsgBody) {
        try {
            messageHandler.handle(imMsgBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
