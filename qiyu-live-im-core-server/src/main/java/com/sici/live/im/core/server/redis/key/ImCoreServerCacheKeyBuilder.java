package com.sici.live.im.core.server.redis.key;

import com.sici.framework.redis.key.RedisKeyBuilder;
import com.sici.framework.redis.key.RedisKeyLoadMatch;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Configurable
@Component
@Conditional(RedisKeyLoadMatch.class)
public class ImCoreServerCacheKeyBuilder extends RedisKeyBuilder {
    private static String IM_MESSAGE_ACK_KEY = "imAckMap";

    public String buildImMsgAckKey(Long userId, Integer appId) {
        return super.getPrefix() + IM_MESSAGE_ACK_KEY + super.getSplitItem() +
                appId + super.getSplitItem() +
                (userId % 100);
    }
}