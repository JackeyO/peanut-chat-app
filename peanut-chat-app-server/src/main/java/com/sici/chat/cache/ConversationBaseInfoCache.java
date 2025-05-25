package com.sici.chat.cache;

import com.sici.chat.builder.cache.ConversationBaseCacheKeyBuilder;
import com.sici.chat.dao.ConversationDao;
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
public class ConversationBaseInfoCache extends AbstractRedisStringCache<Long, ConversationBaseCacheInfo> {
    @Resource
    private ConversationBaseCacheKeyBuilder conversationBaseCacheKeyBuilder;
    @Resource
    private ConversationDao conversationDao;

    @Override
    public String getKey(Long req) {
        return conversationBaseCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return conversationBaseCacheKeyBuilder.getExpireTime().getSeconds();
    }

    @Override
    public Map<Long, ConversationBaseCacheInfo> loadFromDb(List<Long> req) {
        List<UserConversation> conversations = conversationDao.getByConversationId(req);
        return req.stream()
                .collect(Collectors.toMap(
                        conversationId -> conversationId,
                        conversationId -> {
                            UserConversation userConversation = conversations.get(req.indexOf(conversationId));
                            return ConversationBaseCacheInfo.builder()
                                    .conversation(userConversation)
                                    .build();
                        }
                ));
    }
}
