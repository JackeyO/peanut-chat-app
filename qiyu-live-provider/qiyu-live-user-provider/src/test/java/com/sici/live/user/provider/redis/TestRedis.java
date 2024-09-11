package com.sici.live.user.provider.redis;

import com.sici.framework.redis.CacheService;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.user.provider.redis.key.UserProviderCacheKeyBuilder;
import jakarta.annotation.Resource;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test1() {
        cacheService.set("hello", "world");
    }
}
