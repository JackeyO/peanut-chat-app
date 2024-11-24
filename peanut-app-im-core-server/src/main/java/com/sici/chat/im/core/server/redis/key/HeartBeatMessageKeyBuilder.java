package com.sici.chat.im.core.server.redis.key;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_PREFIX;
import static com.sici.common.constant.redis.key.RedisKeyConstant.ALL_KEY_SPLIT_ITEM;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.redis.key
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 2:45 PM
 * @version: 1.0
 */

@Component
public class HeartBeatMessageKeyBuilder {
    private static String HEAR_BEAT_MESSAGE__KEY = "imOnlineZset";
    private static final Integer HEAR_BEAT_MESSAGE__KEY_MOD = 10000;

    /**
     * 按照id%1000去定位到ZSet
     * @param userId
     * @return
     */
    public String buildHearBeatKey(Long userId, Integer appId) {
        return getPrefix() + HEAR_BEAT_MESSAGE__KEY + ALL_KEY_SPLIT_ITEM +
                appId + ALL_KEY_SPLIT_ITEM +
                (userId % HEAR_BEAT_MESSAGE__KEY_MOD);
    }
    public List<String> buildHearBeatKey(List<Long> userId, Integer appId) {
        List<String> keys = new ArrayList<>();
        userId.forEach(id -> keys.add(buildHearBeatKey(id, appId)));
        return keys;
    }
    public String getPrefix() {
        return ALL_KEY_PREFIX;
    }
}
