package com.sici.chat.cache;

import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.builder.cache.RoomMemberCacheKeyBuilder;
import com.sici.chat.dao.RoomMemberDao;
import com.sici.chat.model.chat.room.cache.RoomMemberCacheInfo;
import com.sici.framework.redis.batch.string.AbstractRedisStringCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.cache
 * @author: 20148
 * @description: 双人私聊房间缓存
 * @create-date: 12/2/2024 5:15 PM
 * @version: 1.0
 */

@Component
public class TwoPersonRoomMemberCache extends AbstractRedisStringCache<Long, RoomMemberCacheInfo> {
    @Resource
    private RoomMemberDao roomMemberDao;
    @Resource
    private RoomMemberCacheKeyBuilder roomMemberCacheKeyBuilder;
    @Resource
    private MessageViewAdapter messageViewAdapter;

    @Override
    public String getKey(Long req) {
        return roomMemberCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return roomMemberCacheKeyBuilder.getExpireTime().toSeconds();
    }

    @Override
    public Map<Long, RoomMemberCacheInfo> loadFromDb(List<Long> req) {
        Map<Long, List<Long>> twoPrivateGroupMember = roomMemberDao.getTwoPrivateGroupMember(req);

        Map<Long, RoomMemberCacheInfo> result = twoPrivateGroupMember.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), new RoomMemberCacheInfo(entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return result;
    }
}
