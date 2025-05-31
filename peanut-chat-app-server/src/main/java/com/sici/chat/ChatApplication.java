package com.sici.chat;

import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat
 * @author: 20148
 * @description:
 * @create-date: 11/28/2024 4:03 PM
 * @version: 1.0
 */

@SpringBootApplication
@MapperScan("com.sici.chat.mapper")
@ComponentScan("com.sici")
@CrossOrigin
@EnableAsync(proxyTargetClass = true)
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}