package com.sici.redis;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.redis
 * @author: 20148
 * @description: redis缓存过期时间相关工具类
 * @create-date: 9/11/2024 6:23 PM
 * @version: 1.0
 */

public class RedisExpireTimeUtil {
    public static Long createRandomExpireTime() {
        int time = ThreadLocalRandom.current().nextInt(1000);
        return (long) (time + 60 * 30);
    }
}
