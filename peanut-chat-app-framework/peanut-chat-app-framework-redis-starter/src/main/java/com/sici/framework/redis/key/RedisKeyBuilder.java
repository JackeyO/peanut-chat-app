package com.sici.framework.redis.key;

import java.time.Duration;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.redis.key
 * @author: 20148
 * @description: key生成器
 * @create-date: 12/2/2024 3:11 PM
 * @version: 1.0
 */

public interface RedisKeyBuilder<IN, OUT> {

    /**
     * @param req 请求参数
     * @return key
     */
    OUT build(IN req);
    Duration getExpireTime();
}