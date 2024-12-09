package com.sici.chat.builder.cache;

import com.sici.framework.redis.key.RedisKeyBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_PREFIX;
import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_SPLIT_ITEM;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.builder.cache
 * @author: 20148
 * @description: 用户登陆码生成值
 * @create-date: 12/9/2024 3:07 PM
 * @version: 1.0
 */

@Component
public class UserLoginCodeKeyBuilder implements RedisKeyBuilder {
    @Override
    public String build(Object req) {
        return ALL_KEY_PREFIX + ALL_KEY_SPLIT_ITEM
                + "loginCode";
    }

    @Override
    public Duration getExpireTime() {
        return Duration.ofHours(2);
    }
}
