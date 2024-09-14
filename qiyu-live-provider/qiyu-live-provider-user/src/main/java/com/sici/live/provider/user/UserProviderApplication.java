package com.sici.live.provider.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.provider
 * @author: 20148
 * @description:
 * @create-date: 9/9/2024 9:16 PM
 * @version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@ComponentScan("com.sici")
@MapperScan("com.sici.live.provider.user.mapper")
public class UserProviderApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(UserProviderApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}