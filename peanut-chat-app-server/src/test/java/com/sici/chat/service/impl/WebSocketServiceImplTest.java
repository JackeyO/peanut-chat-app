package com.sici.chat.service.impl;

import com.sici.chat.builder.cache.UserLoginCodeKeyBuilder;
import com.sici.framework.redis.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Time;
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