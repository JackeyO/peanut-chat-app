package com.sici.live.framework.rocketmq.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rocketmq.producer")
@Component
@Data
public class RocketMQProducerProperties {
    //    rocketmq 的 nameSever 地址
    @Value("${rocketmq.producer.nameServer}")
    private String nameServer;
    //分组名称
    @Value("${rocketmq.producer.groupName}")
    private String groupName;
    //消息重发次数
    @Value("${rocketmq.producer.retryTimes:3}")
    private int retryTimes;
    //发送超时时间
    @Value("${rocketmq.producer.sendTimeOut:3000}")
    private int sendTimeOut;
    // 异步线程池核心数
    @Value("${rocketmq.producer.corePoolSize:100}")
    private int corePoolSize;
    // 异步线程池最大线程数
    @Value("${rocketmq.producer.maximumPoolSize:150}")
    private int maximumPoolSize;
    // 异步线程池最大存活时间(分钟)
    @Value("${rocketmq.producer.keepAliveTime:3}")
    private long keepAliveTime;
    // 阻塞队列初始容量
    @Value("${rocketmq.producer.capacity:1000}")
    private int capacity;
}