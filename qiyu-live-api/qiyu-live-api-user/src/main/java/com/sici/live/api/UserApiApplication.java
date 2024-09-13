package com.sici.live.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.api
 * @author: 20148
 * @description:
 * @create-date: 9/10/2024 3:19 PM
 * @version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.sici")
public class UserApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }
}
