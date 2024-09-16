package com.sici.live.provider.im;

import com.sici.common.result.ResponseResult;
import com.sici.live.provider.im.service.ImTokenService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 5:05 PM
 * @version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@ComponentScan("com.sici")
@Slf4j
public class ImProviderApplication {
    @Resource
    private ImTokenService imTokenService;
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ImProviderApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}
