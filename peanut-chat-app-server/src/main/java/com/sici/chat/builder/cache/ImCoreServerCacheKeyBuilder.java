package com.sici.chat.builder.cache;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_PREFIX;
import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_SPLIT_ITEM;

@Configurable
@Component
public class ImCoreServerCacheKeyBuilder {
    private static String IM_MESSAGE_ACK_KEY = "imAckMap";

    public String buildImMsgAckKey(Integer userId) {
        return getPrefix() + ALL_KEY_PREFIX + IM_MESSAGE_ACK_KEY + ALL_KEY_SPLIT_ITEM +
                 ALL_KEY_SPLIT_ITEM +
                (userId % 100);
    }

    public String getPrefix() {
        return ALL_KEY_PREFIX;
    }
}