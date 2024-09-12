package com.sici.live.framework.rocketmq.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "qiyu.rocketmq.producer")
@Component
@Data
public class RocketMQProducerProperties {
    //rocketmq 的 nameSever 地址
    @Value("${qiyu.rocketmq.producer.nameSrv}")
    private String nameSrv;
    //分组名称
    @Value("${qiyu.rocketmq.producer.groupName}")
    private String groupName;
    //消息重发次数
    @Value("${qiyu.rocketmq.producer.retryTimes:3}")
    private int retryTimes;
    //发送超时时间
    @Value("${qiyu.rocketmq.producer.sendTimeOut:3000}")
    private int sendTimeOut;
    // 异步线程池核心数
    @Value("${qiyu.rocketmq.producer.corePoolSize:100}")
    private int corePoolSize;
    // 异步线程池最大线程数
    @Value("${qiyu.rocketmq.producer.maximumPoolSize:150}")
    private int maximumPoolSize;
    // 异步线程池最大存活时间(分钟)
    @Value("${qiyu.rocketmq.producer.keepAliveTime:3}")
    private long keepAliveTime;
    // 阻塞队列初始容量
    @Value("${qiyu.rocketmq.producer.capacity:1000}")
    private int capacity;
}