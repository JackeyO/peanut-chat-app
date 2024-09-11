package com.sici.live.user.provider.redis;

import com.sici.framework.redis.CacheService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.redis
 * @author: 20148
 * @description:
 * @create-date: 9/11/2024 12:59 PM
 * @version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
    @Resource
    private CacheService cacheService;

    @Test
    public void test1() {
        cacheService.set("hello", "world");
        String s = cacheService.get("hello");
    }
}
