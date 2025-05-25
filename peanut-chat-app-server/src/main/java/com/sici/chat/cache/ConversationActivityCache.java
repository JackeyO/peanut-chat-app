package com.sici.chat.cache;

import com.sici.chat.builder.cache.ConversationActivityCacheKeyBuilder;
import com.sici.chat.builder.cache.ConversationBaseCacheKeyBuilder;
import com.sici.chat.dao.ConversationDao;
import com.sici.chat.model.chat.conversation.cache.ConversationActivityCacheInfo;
import com.sici.chat.model.chat.conversation.cache.ConversationBaseCacheInfo;
import com.sici.chat.model.chat.conversation.entity.UserConversation;
import com.sici.framework.redis.batch.AbstractRedisStringCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jackey
 * @description 会话缓存
 * @date 5/25/25 12:43
 */
@Component
public class ConversationActivityCache extends AbstractRedisStringCache<Long, ConversationActivityCacheInfo> {
    @Resource
    private ConversationActivityCacheKeyBuilder conversationActivityCacheKeyBuilder;
    @Override
    public String getKey(Long req) {
        return conversationActivityCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return conversationActivityCacheKeyBuilder.getExpireTime().getSeconds();
    }

    @Override
    public Map<Long, ConversationActivityCacheInfo> loadFromDb(List<Long> req) {
        // 不在数据库存储，永远存在redis
        return Map.of();
    }
}
