package com.sici.chat.mq;

import com.sici.chat.framework.rmq.config.MQProducer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jackey
 * @description mq收发消息测试
 * @date 5/23/25 17:31
 */
@SpringBootTest
@Slf4j
public class MqTest {
    @Resource
    private MQProducer mqProducer;

    @Test
    public void testSend() {
        String message = "Hello, RabbitMQ!";
        mqProducer.sendMsg("hello", message);
    }
}
