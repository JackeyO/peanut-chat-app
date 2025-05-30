package com.sici.chat.config.canal;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Canal配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "peanut.canal")
public class CanalConfigurationProperties {
    
    /**
     * Canal服务器地址
     */
    private String serverHost;
    
    /**
     * Canal服务器端口
     */
    private Integer serverPort;
    
    /**
     * Canal实例名称
     */
    private String destination;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 批量获取数据大小
     */
    private Integer batchSize;
    
    /**
     * 获取数据超时时间（毫秒）
     */
    private Long timeout;
    
    /**
     * 是否启用Canal监听
     */
    private Boolean enabled;
    
    /**
     * 监听的数据库表配置
     */
    private List<TableConfig> tables;
    
    /**
     * 重试配置
     */
    private RetryConfig retry = new RetryConfig();
    
    @Data
    public static class TableConfig {
        /**
         * 数据库名
         */
        private String database;
        
        /**
         * 表名
         */
        private String table;
        
        /**
         * 主键字段名
         */
        private String primaryKey = "id";
        
        /**
         * 是否启用
         */
        private Boolean enabled = true;
        
        /**
         * 自定义配置
         */
        private Map<String, Object> properties;
    }
    
    @Data
    public static class RetryConfig {
        /**
         * 最大重试次数
         */
        private Integer maxRetryTimes;
        
        /**
         * 重试间隔（毫秒）
         */
        private Long retryInterval;
        
        /**
         * 是否启用指数退避
         */
        private Boolean exponentialBackoff;
    }
}