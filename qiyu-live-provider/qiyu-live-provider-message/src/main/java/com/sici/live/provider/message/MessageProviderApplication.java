package com.sici.live.provider.message;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.message
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 3:28 PM
 * @version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@ComponentScan("com.sici")
public class MessageProviderApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MessageProviderApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}
