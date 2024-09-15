package com.sici.framework.datasource.config;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.jdbc.core.driver.ShardingSphereDriverURLProvider;
import org.springframework.util.StringUtils;

import javax.print.attribute.standard.MediaSize;
import java.util.Properties;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.datasource.config
 * @author: 20148
 * @description: 基于SPI机制针对shardingJdbc进行二次开发，使其支持从nacos读配置内容
 * @create-date: 9/15/2024 4:02 PM
 * @version: 1.0
 */

@Slf4j
public class NacosDriverURLProvider implements ShardingSphereDriverURLProvider {
    private static final String NACOS_TYPE = "nacos:";
    private static final String NACOS_GROUP = "DEFAULT_GROUP";
    @Override
    public boolean accept(String url) {
        return !StringUtils.isEmpty(url) && url.contains(NACOS_TYPE);
    }

    @Override
    public byte[] getContent(String url) {
        if (StringUtils.isEmpty(url)) return null;

        String nacosUrl = url.substring(url.lastIndexOf(NACOS_TYPE) + NACOS_TYPE.length());

        String[] nacosUrlSplit = nacosUrl.split(":");

        String nacosPath = nacosUrlSplit[2];

        String[] nacosPathSplit = nacosPath.split("\\?");

        String data_id = nacosPathSplit[0];

        String[] nacosProperties = nacosPathSplit[1].split("&&");

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, nacosUrlSplit[0] + ":" + nacosUrlSplit[1]);
        for (String nacosProperty : nacosProperties) {
            String[] propertySplit = nacosProperty.split("=");
            String key = propertySplit[0];
            String value = propertySplit[1];
            properties.put(key, value);
        }

        ConfigService configService = null;
        try {
            configService = ConfigFactory.createConfigService(properties);
            String config = configService.getConfig(data_id, NACOS_GROUP, 6000);
            log.info("从Nacos配置中心读取到sharding-jdbc配置:{}", config);
            return config.getBytes();
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}