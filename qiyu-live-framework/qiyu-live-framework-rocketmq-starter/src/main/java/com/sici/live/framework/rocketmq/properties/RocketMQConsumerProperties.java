package com.sici.live.framework.rocketmq.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "qiyu.rocketmq.consumer")
@Component
@Data
public class RocketMQConsumerProperties {
    //rocketmq 的 nameSever 地址
    @Value("${qiyu.rocketmq.consumer.nameSrv}")
    private String nameSrv;
    //分组名称
    @Value("${qiyu.rocketmq.consumer.groupName}")
    private String groupName;
}