package com.sici.framework.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.redis.config
 * @author: 20148
 * @description:
 * @create-date: 9/11/2024 12:50 PM
 * @version: 1.0
 */

@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object>
    redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new
                RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        IGenericJackson2JsonRedisSerializer valueSerializer = new
                IGenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new
                StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
