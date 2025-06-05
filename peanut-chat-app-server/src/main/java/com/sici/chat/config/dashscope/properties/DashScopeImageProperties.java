package com.sici.chat.config.dashscope.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author jackey
 * @description
 * @date 6/5/25 22:14
 */
@EnableConfigurationProperties(DashScopeImageProperties.class)
@ConfigurationProperties(prefix = "peanut.ai.dashscope.image")
@Data
public class DashScopeImageProperties extends DashScopeParentProperties{
    private String model;
}