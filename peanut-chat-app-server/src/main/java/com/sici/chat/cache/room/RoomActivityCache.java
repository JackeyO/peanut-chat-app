package com.sici.chat.cache.room;

import com.sici.chat.builder.cache.conversation.ConversationActivityCacheKeyBuilder;
import com.sici.chat.builder.cache.room.RoomActivityCacheKeyBuilder;
import com.sici.chat.model.chat.conversation.cache.ConversationActivityCacheInfo;
import com.sici.chat.model.chat.room.cache.RoomActivityCacheInfo;
import com.sici.framework.redis.batch.string.AbstractRedisStringCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author jackey
 * @description 会话缓存
 * @date 5/25/25 12:43
 */
@Component
public class RoomActivityCache extends AbstractRedisStringCache<Long, RoomActivityCacheInfo> {
    @Resource
    private RoomActivityCacheKeyBuilder roomActivityCacheKeyBuilder;
    @Override
    public String getKey(Long req) {
        return roomActivityCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return roomActivityCacheKeyBuilder.getExpireTime().getSeconds();
    }

    @Override
    public Map<Long, RoomActivityCacheInfo> loadFromDb(List<Long> req) {
        // 不在数据库存储，永远存在redis
        return Map.of();
    }
}
