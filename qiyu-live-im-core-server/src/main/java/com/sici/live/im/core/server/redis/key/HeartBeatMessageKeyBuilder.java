package com.sici.live.im.core.server.redis.key;

import com.sici.framework.redis.key.RedisKeyBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.redis.key
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 2:45 PM
 * @version: 1.0
 */

@Component
public class HeartBeatMessageKeyBuilder extends RedisKeyBuilder {
    private static String HEAR_BEAT_MESSAGE__KEY = "imOnlineZset";
    private static final Integer HEAR_BEAT_MESSAGE__KEY_MOD = 10000;

    /**
     * 按照id%1000去定位到ZSet
     * @param userId
     * @return
     */
    public String buildHearBeatKey(Long userId, Integer appId) {
        return super.getPrefix() + HEAR_BEAT_MESSAGE__KEY + super.getSplitItem() +
                appId + super.getSplitItem() +
                (userId % HEAR_BEAT_MESSAGE__KEY_MOD);
    }

    public List<String> buildHearBeatKey(List<Long> userId, Integer appId) {
        List<String> keys = new ArrayList<>();
        userId.forEach(id -> keys.add(buildHearBeatKey(id, appId)));
        return keys;
    }
}
