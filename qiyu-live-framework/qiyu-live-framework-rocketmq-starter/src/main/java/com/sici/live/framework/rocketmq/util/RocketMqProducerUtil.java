package com.sici.live.framework.rocketmq.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common.util
 * @author: 20148
 * @description:
 * @create-date: 9/19/2024 2:51 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class RocketMqProducerUtil {
    @Resource
    private MQProducer mqProducer;
    private Message buildMessage(String data, String topic) {
        Message message = new Message();
        message.setTopic(topic);
        message.setBody(data.getBytes());
        return message;
    }

    private Message buildMessageDelay(String data, String topic, Integer delayLevel) {
        Message message = new Message();
        message.setTopic(topic);
        message.setBody(data.getBytes());
        message.setDelayTimeLevel(delayLevel);
        return message;
    }

    private void sendMessage(Message message) {
        String topic = message.getTopic();
        try {
            SendResult send = mqProducer.send(message);
            if (send.getSendStatus().equals(SendStatus.SEND_OK)) {
                log.info("[rocketmq-producerUtil]==>发送消息到topic:{}成功,消息内容:{}",
                        topic,
                        message);
            } else {
                log.error("[rocketmq-producerUtil]==>发送消息到topic:{}失败,消息内容:{}",
                        topic,
                        message);
            }
        } catch (Exception e) {
            log.error("[rocketmq-producerUtil]==>发送消息到topic:{}失败,消息内容:{}",
                    topic,
                    message);
        }
    }

    public void sendMessage(String topic, String data) {
        Message message = buildMessage(data, topic);
        sendMessage(message);
    }

    public void sendMessageDelay(String topic, String data, Integer delayLevel) {
        Message message = buildMessageDelay(data, topic, delayLevel);
        sendMessage(message);
    }
}
