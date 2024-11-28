package com.sici.framework.redis.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.utilsredis.value
 * @author: 20148
 * @description: utilsredis value
 * @create-date: 9/11/2024 5:56 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RedisKeyEntry {
    // 统一用string存
    String value;
    Long expire;
    TimeUnit timeUnit;
}
