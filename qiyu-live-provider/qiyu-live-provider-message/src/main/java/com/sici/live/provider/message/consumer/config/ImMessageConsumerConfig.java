package com.sici.live.provider.message.consumer.config;

import com.sici.common.constant.im.ImMqConstant;
import com.sici.live.framework.rocketmq.config.RocketMQConsumerConfigurer;
import com.sici.live.provider.message.consumer.ImMessageReceiveConsumer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.message.consumer.config
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:31 PM
 * @version: 1.0
 */

@Configuration
@Slf4j
public class ImMessageConsumerConfig implements InitializingBean {
    @Resource
    private RocketMQConsumerConfigurer rocketMQConsumerConfigurer;
    @Resource
    private ImMessageReceiveConsumer imMessageReceiveConsumer;

    @Override
    public void afterPropertiesSet() {
        initConsumer();
    }

    public void initConsumer() {
        try {
            //初始化我们的 RocketMQ 消费者
            DefaultMQPushConsumer defaultMQPushConsumer = rocketMQConsumerConfigurer.configureDefault();
            defaultMQPushConsumer.setConsumerGroup(ImMqConstant.IM_CORE_SERVER_MESSAGE_RECEIVE_CONSUMER_GROUP);
            defaultMQPushConsumer.subscribe(ImMqConstant.IM_CORE_SERVER_MESSAGE_RECEIVE_TOPIC, "*");
            defaultMQPushConsumer.setMessageListener(imMessageReceiveConsumer);
            defaultMQPushConsumer.start();
            log.info("[mq==>IM消息接收==>消费者==>启动成功], nameSrv is {}", defaultMQPushConsumer.getNamesrvAddr());
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
}
