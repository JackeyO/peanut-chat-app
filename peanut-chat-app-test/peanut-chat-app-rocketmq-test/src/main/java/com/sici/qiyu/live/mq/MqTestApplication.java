package com.sici.qiyu.live.mq;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



/**
 * @projectName: qiyu-live-app
 * @package: com.sici.qiyu.live.mq
 * @author: 20148
 * @description:
 * @create-date: 11/20/2024 3:57 PM
 * @version: 1.0
 */

@SpringBootApplication
@ComponentScan("com.sici")
public class MqTestApplication implements CommandLineRunner {
    @Resource
    private MQProducer mqProducer;
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MqTestApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        mqProducer.sendMsg("yes", "hello");
    }
}
