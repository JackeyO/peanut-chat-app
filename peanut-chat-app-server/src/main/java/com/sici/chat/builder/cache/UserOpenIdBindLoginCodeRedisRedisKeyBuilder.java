package com.sici.chat.builder.cache;

import com.sici.common.constant.redis.key.RedisKeyConstant;
import com.sici.framework.redis.key.RedisKeyBuilder;

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

public class UserOpenIdBindLoginCodeRedisRedisKeyBuilder implements RedisKeyBuilder<String, String> {
    @Override
    public String build(String req) {
        return ALL_KEY_PREFIX + ALL_KEY_SPLIT_ITEM
                + "user-openid-bind-login-code" + ALL_KEY_SPLIT_ITEM
                + req;
    }
}