package com.sici.chat.builder.cache.user;

import com.sici.framework.redis.key.RedisKeyBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_PREFIX;
import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_SPLIT_ITEM;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.builder.cache
 * @author: 20148
 * @description: 用户openid与登录code的绑定
 * @create-date: 12/6/2024 2:01 PM
 * @version: 1.0
 */
@Component
public class UserOpenIdBindLoginCodeKeyBuilder implements RedisKeyBuilder<String, String> {
    @Override
    public String build(String req) {
        return ALL_KEY_PREFIX + ALL_KEY_SPLIT_ITEM
                + "user-openid-bind-login-code" + ALL_KEY_SPLIT_ITEM
                + req;
    }

    @Override
    public Duration getExpireTime() {
        return Duration.ofSeconds(50);
    }
}