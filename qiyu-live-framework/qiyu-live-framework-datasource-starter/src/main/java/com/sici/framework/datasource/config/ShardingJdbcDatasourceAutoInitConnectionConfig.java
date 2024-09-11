package com.sici.framework.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.config
 * @author: 20148
 * @description: shardingJdbc配置类
 * @create-date: 9/10/2024 5:53 PM
 * @version: 1.0
 */

@Configuration
@Slf4j
public class ShardingJdbcDatasourceAutoInitConnectionConfig {
    @Bean
    public ApplicationRunner runner(DataSource dataSource) {
        return args -> {
            log.info("initialize dataSource: {}", dataSource);
            Connection connection = dataSource.getConnection();
        };
    }
}