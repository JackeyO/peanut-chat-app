package com.sici.chat.wechat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.wechat
 * @author: 20148
 * @description:
 * @create-date: 12/4/2024 4:19 PM
 * @version: 1.0
 */

@SpringBootApplication
@ComponentScan("com.sici")
public class WechatTestApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WechatTestApplication.class);
        springApplication.run(args);
    }
}
