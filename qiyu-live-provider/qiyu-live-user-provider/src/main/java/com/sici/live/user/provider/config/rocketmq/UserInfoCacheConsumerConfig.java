package com.sici.live.user.provider.config.rocketmq;

import com.sici.common.constant.user.UserProviderConstant;
import com.sici.live.user.provider.consumer.UserInfoCacheConsumer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
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
public class UserInfoCacheConsumerConfig implements InitializingBean {
    @Resource
    private RocketMQConsumerConfigurer rocketMQConsumerConfigurer;
    @Resource
    private UserInfoCacheConsumer userInfoCacheConsumer;
    @Override
    public void afterPropertiesSet() throws Exception {
        initConsumer();
    }

    public void initConsumer() {
        try {
            //初始化我们的 RocketMQ 消费者
            DefaultMQPushConsumer defaultMQPushConsumer = rocketMQConsumerConfigurer.configureDefault();
            defaultMQPushConsumer.subscribe(UserProviderConstant.USER_CACHE_DELETE_TOPIC, "*");
            defaultMQPushConsumer.setMessageListener(userInfoCacheConsumer);
            defaultMQPushConsumer.start();
            log.info("mq==>用户信息缓存删除消费者启动成功,nameSrv is {}", defaultMQPushConsumer.getNamesrvAddr());
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
}

