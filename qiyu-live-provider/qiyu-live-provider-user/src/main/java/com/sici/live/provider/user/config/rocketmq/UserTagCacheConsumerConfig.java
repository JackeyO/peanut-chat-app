package com.sici.live.provider.user.config.rocketmq;

import com.sici.common.constant.user.UserProviderConstant;
import com.sici.live.framework.rocketmq.config.RocketMQConsumerConfigurer;
import com.sici.live.provider.user.consumer.UserInfoCacheConsumer;
import com.sici.live.provider.user.consumer.UserTagCacheConsumer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName:    qiyu-live-app
 * @package:        com.sici.live.user.provider.config.rocketmq
 * @author:         20148
 * @description:
 * @create-date:    9/12/2024 4:55 PM
 * @version:        1.0
 */

@Configuration
@Slf4j
public class UserTagCacheConsumerConfig implements InitializingBean {
    @Resource
    private RocketMQConsumerConfigurer rocketMQConsumerConfigurer;
    @Resource
    private UserTagCacheConsumer userTagCacheConsumer;
    @Override
    public void afterPropertiesSet() {
        initConsumer();
    }

    public void initConsumer() {
        try {
            //初始化我们的 RocketMQ 消费者
            DefaultMQPushConsumer defaultMQPushConsumer = rocketMQConsumerConfigurer.configureDefault();
            defaultMQPushConsumer.setConsumerGroup(UserProviderConstant.USER_TAG_CONSUMER_GROUP);
            defaultMQPushConsumer.subscribe(UserProviderConstant.USER_TAG_CACHE_DELETE_TOPIC, "*");
            defaultMQPushConsumer.setMessageListener(userTagCacheConsumer);
            defaultMQPushConsumer.start();
            log.info("mq==>用户标签信息缓存删除消费者启动成功,nameSrv is {}", defaultMQPushConsumer.getNamesrvAddr());
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
}

