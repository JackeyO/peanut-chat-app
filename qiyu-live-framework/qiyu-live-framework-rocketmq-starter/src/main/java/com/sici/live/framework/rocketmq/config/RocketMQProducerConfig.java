package com.sici.live.framework.rocketmq.config;

import com.sici.live.framework.rocketmq.properties.RocketMQProducerProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
@Slf4j
public class RocketMQProducerConfig {
    @Resource
    private RocketMQProducerProperties producerProperties;
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public MQProducer mqProducer() {
        ThreadPoolExecutor asyncThreadPoolExecutor = new ThreadPoolExecutor(producerProperties.getCorePoolSize(),
                producerProperties.getMaximumPoolSize(),
                producerProperties.getKeepAliveTime(),
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(producerProperties.getCapacity()),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName(applicationName + ":rmq-producer:" + ThreadLocalRandom.current().nextInt(1000));
                    return thread;
                });
        //初始化 rocketmq 的生产者
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        try {
            defaultMQProducer.setNamesrvAddr(producerProperties.getNameServer());
            defaultMQProducer.setProducerGroup(producerProperties.getGroupName());
            defaultMQProducer.setRetryTimesWhenSendFailed(producerProperties.getRetryTimes());
            defaultMQProducer.setRetryTimesWhenSendAsyncFailed(producerProperties.getRetryTimes());
            defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(true);
            defaultMQProducer.setSendMsgTimeout(producerProperties.getSendTimeOut());
            //设置异步发送的线程池
            defaultMQProducer.setAsyncSenderExecutor(asyncThreadPoolExecutor);
            defaultMQProducer.start();
            log.info("rocketmq 生产者启动成功,nameSrv is {}", producerProperties.getNameServer());
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return defaultMQProducer;
    }
}