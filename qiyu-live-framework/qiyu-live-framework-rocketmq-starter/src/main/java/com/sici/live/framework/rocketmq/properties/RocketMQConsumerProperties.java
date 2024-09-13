package com.sici.live.framework.rocketmq.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rocketmq.consumer")
@Component
@Data
public class RocketMQConsumerProperties {
//    rocketmq 的 nameSever 地址
    @Value("${rocketmq.consumer.nameServer}")
    private String nameServer;
    //分组名称
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
}