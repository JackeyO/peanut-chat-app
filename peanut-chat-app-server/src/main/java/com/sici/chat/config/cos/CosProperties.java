package com.sici.chat.config.cos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author jackey
 * @description
 * @date 6/6/25 20:41
 */
@Component
@ConfigurationProperties(prefix = "peanut.cos")
@Data
public class CosProperties {
    private String secretId;
    private String secretKey;
    private String region;
}
