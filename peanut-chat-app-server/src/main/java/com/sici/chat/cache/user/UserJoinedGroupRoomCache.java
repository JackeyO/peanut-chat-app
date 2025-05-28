package com.sici.chat.cache.user;

import com.sici.chat.builder.cache.user.UserJoinedRoomCacheKeyBuilder;
import com.sici.chat.builder.cache.user.UserOpenIdBindLoginCodeKeyBuilder;
import com.sici.chat.cache.room.RoomBaseInfoCache;
import com.sici.chat.dao.RoomMemberDao;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.chat.model.user.cache.UserJoinedGroupRoomCacheInfo;
import com.sici.common.enums.chat.room.RoomTypeEnums;
import com.sici.framework.redis.batch.string.AbstractRedisStringCache;
import jakarta.annotation.Resource;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jackey
 * @description
 * @date 5/28/25 16:18
 */
@Component
public class UserJoinedGroupRoomCache extends AbstractRedisStringCache<Long, UserJoinedGroupRoomCacheInfo> {
    @Resource
    private UserJoinedRoomCacheKeyBuilder userJoinedRoomCacheKeyBuilder;
    @Resource
    private RoomMemberDao roomMemberDao;
    @Resource
    private UserOpenIdBindLoginCodeKeyBuilder userOpenIdBindLoginCodeKeyBuilder;
    @Resource
    private RoomBaseInfoCache roomBaseInfoCache;

    @Override
    public String getKey(Long req) {
        return userJoinedRoomCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return userJoinedRoomCacheKeyBuilder.getExpireTime().getSeconds();
    }

    @Override
    public Map<Long, UserJoinedGroupRoomCacheInfo> loadFromDb(List<Long> req) {
        // 获取用户加入的所有房间id
        List<List<Long>> rooms = req.stream()
                .map(userId -> Optional.ofNullable(roomMemberDao.getRoomIdByUserId(userId)).orElse(Lists.newArrayList()))
                .collect(Collectors.toList());

        // 过滤掉私聊房间
        List<List<Room>> groupRoomsInfo = rooms.stream()
                .map(roomIds -> {
                    Map<Long, Room> roomsInfoMap = roomBaseInfoCache.getBatch(roomIds);
                    // 删掉私聊房间
                    List<Room> groupRoomInfo = roomIds.stream()
                            .filter(roomId -> Optional.ofNullable(roomsInfoMap.get(roomId))
                                    .map(room -> room.getType().equals(RoomTypeEnums.GROUP.getType()))
                                    .orElse(false))
                            .map(roomsInfoMap::get)
                            .collect(Collectors.toList());
                    return groupRoomInfo;
                })
                .collect(Collectors.toList());


        return req.stream()
                .collect(Collectors.toMap(userId -> userId, userId -> UserJoinedGroupRoomCacheInfo
                        .builder()
                        .rooms(groupRoomsInfo.get(groupRoomsInfo.indexOf(userId)))
                        .build())
                );
    }
}
