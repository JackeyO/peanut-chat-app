package com.sici.chat.im.core.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server
 * @author: 20148
 * @description:
 * @create-date: 9/15/2024 9:00 PM
 * @version: 1.0
 */

@SpringBootApplication
@ComponentScan("com.sici")
@EnableDiscoveryClient
@EnableDubbo
public class NettyImServerApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(NettyImServerApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}

