package com.sici.live.provider.id.generate;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.id.generate
 * @author: 20148
 * @description: 分布式id生成器微服务
 * @create-date: 9/12/2024 7:27 PM
 * @version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@ComponentScan("com.sici")
public class IdGenerateApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(IdGenerateApplication.class, args);
    }
}
