package com.sici.qiyu.live.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

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
@RocketMQMessageListener(consumerGroup = "hello", topic = "yes")
public class MessageReceiveConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String imMsgBody) {
        log.info(imMsgBody);
    }
}
