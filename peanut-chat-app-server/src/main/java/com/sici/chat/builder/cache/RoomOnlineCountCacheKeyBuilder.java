package com.sici.chat.builder.cache;

import com.sici.common.constant.redis.key.RedisKeyConstant;
import com.sici.framework.redis.key.RedisKeyBuilder;

import java.time.Duration;

import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_PREFIX;
import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_SPLIT_ITEM;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.builder.cache
 * @author: 20148
 * @description: 房间在线用户数缓存key生成器
 * @create-date: 12/23/2024 5:01 PM
 * @version: 1.0
 */

public class RoomOnlineCountCacheKeyBuilder implements RedisKeyBuilder<Integer, String> {
    @Override
    public String build(Integer roomId) {
        return ALL_KEY_PREFIX + ALL_KEY_SPLIT_ITEM
                + roomId;
    }

    @Override
    public Duration getExpireTime() {
        return null;
    }
}
