package com.sici.chat.builder.cache;

import com.sici.common.constant.redis.key.RedisKeyConstant;
import com.sici.framework.redis.key.RedisKeyBuilder;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author jackey
 * @description 对话缓存key构建器
 * @date 5/25/25 14:26
 */
@Component
public class ConversationBaseCacheKeyBuilder implements RedisKeyBuilder<Long, String> {
    @Override
    public String build(Long req) {
        return RedisKeyConstant.ALL_KEY_PREFIX + RedisKeyConstant.ALL_KEY_SPLIT_ITEM
                + "conversation" + RedisKeyConstant.ALL_KEY_SPLIT_ITEM + req;
    }

    @Override
    public Duration getExpireTime() {
        return Duration.ofDays(1);
    }
}
