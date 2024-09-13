package com.sici.live.framework.rocketmq.config;

import com.sici.live.framework.rocketmq.properties.RocketMQConsumerProperties;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.config.rocketmq
 * @author: 20148
 * @description: 消费者配置器
 * @create-date: 9/12/2024 4:45 PM
 * @version: 1.0
 */

@Component
public class RocketMQConsumerConfigurer {
    @Resource
    private RocketMQConsumerProperties consumerProperties;

    public DefaultMQPushConsumer configureDefault() {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setNamesrvAddr(consumerProperties.getNameServer());
        defaultMQPushConsumer.setConsumerGroup(consumerProperties.getGroupName());
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        return defaultMQPushConsumer;
    }
}
