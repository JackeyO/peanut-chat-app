package com.sici.chat.cache;

import com.sici.chat.builder.cache.RoomMemberCacheKeyBuilder;
import com.sici.chat.dao.RoomMemberDao;
import com.sici.chat.model.chat.message.vo.RoomMemberListBo;
import com.sici.framework.redis.batch.AbstractRedisStringCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.cache
 * @author: 20148
 * @description: 群聊房间成员信息缓存
 * @create-date: 12/2/2024 3:08 PM
 * @version: 1.0
 */

@Component
public class GroupRoomMemberCache extends AbstractRedisStringCache<Long, RoomMemberListBo> {
    @Resource
    private RoomMemberCacheKeyBuilder roomMemberCacheKeyBuilder;
    @Resource
    private RoomMemberDao roomMemberDao;

    @Override
    public String getKey(Long req) {
        return roomMemberCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return roomMemberCacheKeyBuilder.getExpireTime().toSeconds();
    }

    @Override
    public Map<Long, RoomMemberListBo> loadFromDb(List<Long> req) {
        Map<Long, List<Long>> groupRoomMember = roomMemberDao.getGroupRoomMember(req);

        Map<Long, RoomMemberListBo> result = groupRoomMember.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), new RoomMemberListBo(entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return result;
    }
}
