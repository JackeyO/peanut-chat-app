package com.sici.chat.im.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:41 PM
 * @version: 1.0
 */

@Slf4j
@SpringBootApplication
@ComponentScan("com.sici")
public class ImClientApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ImClientApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}