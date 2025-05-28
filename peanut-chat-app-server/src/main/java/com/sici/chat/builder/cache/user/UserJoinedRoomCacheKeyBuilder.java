package com.sici.chat.builder.cache.user;

import com.sici.chat.config.wx.WxMpProperties;
import com.sici.common.constant.redis.key.RedisKeyConstant;
import com.sici.framework.redis.key.RedisKeyBuilder;

import java.time.Duration;

/**
 * @author jackey
 * @description
 * @date 5/28/25 16:21
 */
public class UserJoinedRoomCacheKeyBuilder implements RedisKeyBuilder<Long, String> {
    @Override
    public Duration getExpireTime() {
        return Duration.ofHours(1);
    }

    @Override
    public String build(Long req) {
        return RedisKeyConstant.ALL_KEY_PREFIX + RedisKeyConstant.ALL_KEY_SPLIT_ITEM
                + "user_joined_room" + RedisKeyConstant.ALL_KEY_SPLIT_ITEM
                + req;
    }
}
