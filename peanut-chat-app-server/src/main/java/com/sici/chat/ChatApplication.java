package com.sici.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

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
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}