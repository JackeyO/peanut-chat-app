package com.sici.chat.service.impl;

import com.sici.chat.builder.cache.UserLoginCodeKeyBuilder;
import com.sici.framework.redis.RedisUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class WebSocketServiceImplTest {
    @Resource
    private UserLoginCodeKeyBuilder userLoginCodeKeyBuilder;
    @Test
    void testGenerateLoginCode() {
//        int loginCode = loginCode = RedisUtils.integerInc(userLoginCodeKeyBuilder.build(null), (int) userLoginCodeKeyBuilder.getExpireTime().toSeconds(),
//                TimeUnit.SECONDS);
        Long hello = RedisUtils.inc("hello", 100, TimeUnit.SECONDS);

    }
}