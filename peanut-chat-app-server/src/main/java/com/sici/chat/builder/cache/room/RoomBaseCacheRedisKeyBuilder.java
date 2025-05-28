package com.sici.chat.builder.cache.room;

import com.sici.framework.redis.key.RedisKeyBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_PREFIX;
import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_SPLIT_ITEM;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.builder.cache
 * @author: 20148
 * @description: 房间信息缓存key生成器
 * @create-date: 12/2/2024 3:10 PM
 * @version: 1.0
 */

@Component
public class RoomBaseCacheRedisKeyBuilder implements RedisKeyBuilder<Long, String> {
    @Override
    public String build(Long req) {
        return ALL_KEY_PREFIX + ALL_KEY_SPLIT_ITEM + "room-base-info"
                + ALL_KEY_SPLIT_ITEM + req;
    }
    @Override
    public Duration getExpireTime() {
        return Duration.ofSeconds(50);
    }
}
