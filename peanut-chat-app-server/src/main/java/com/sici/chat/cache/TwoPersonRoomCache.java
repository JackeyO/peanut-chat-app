package com.sici.chat.cache;

import com.sici.chat.builder.cache.RoomMemberCacheRedisKeyBuilder;
import com.sici.chat.dao.RoomMemberDao;
import com.sici.framework.redis.batch.AbstractRedisStringCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.cache
 * @author: 20148
 * @description: 双人私聊房间缓存
 * @create-date: 12/2/2024 5:15 PM
 * @version: 1.0
 */

@Component
public class TwoPersonRoomCache extends AbstractRedisStringCache<Integer, List<Integer>> {
    @Resource
    private RoomMemberDao roomMemberDao;
    @Resource
    private RoomMemberCacheRedisKeyBuilder roomMemberCacheKeyBuilder;

    @Override
    public String getKey(Integer req) {
        return roomMemberCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return 50 * 1000;
    }

    @Override
    public Map<Integer, List<Integer>> loadFromDb(List<Integer> req) {
        return roomMemberDao.getTwoPrivateGroupMember(req);
    }
}
