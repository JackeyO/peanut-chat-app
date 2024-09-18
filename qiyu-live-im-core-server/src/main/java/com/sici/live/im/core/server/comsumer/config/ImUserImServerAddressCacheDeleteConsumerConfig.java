package com.sici.live.im.core.server.comsumer.config;

import com.sici.common.constant.im.ImMqConstant;
import com.sici.live.framework.rocketmq.config.RocketMQConsumerConfigurer;
import com.sici.live.im.core.server.comsumer.ImUserImServerAddressCacheDeleteConsumer;
import com.sici.live.im.core.server.comsumer.ImUserOnlineTimeCacheSaveConsumer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.comsumer.config
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 4:07 PM
 * @version: 1.0
 */

@Configuration
@Slf4j
public class ImUserImServerAddressCacheDeleteConsumerConfig implements InitializingBean {
    @Resource
    private RocketMQConsumerConfigurer rocketMQConsumerConfigurer;
    @Resource
    private ImUserImServerAddressCacheDeleteConsumer imUserImServerAddressCacheDeleteConsumer;

    @Override
    public void afterPropertiesSet() {
        initConsumer();
    }

    public void initConsumer() {
        try {
            //初始化我们的 RocketMQ 消费者
            DefaultMQPushConsumer defaultMQPushConsumer = rocketMQConsumerConfigurer.configureDefault();
            defaultMQPushConsumer.setConsumerGroup(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_DELETE_CONSUMER_GROUP);
            defaultMQPushConsumer.subscribe(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_DELETE_TOPIC, "*");
            defaultMQPushConsumer.setMessageListener(imUserImServerAddressCacheDeleteConsumer);
            defaultMQPushConsumer.start();
            log.info("[mq==>用户连接IM服务器地址缓存删除==>消费者==>启动成功], nameSrv is {}", defaultMQPushConsumer.getNamesrvAddr());
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
}
