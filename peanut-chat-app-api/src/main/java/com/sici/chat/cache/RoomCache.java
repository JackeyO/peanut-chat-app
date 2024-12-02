package com.sici.chat.cache;

import com.sici.chat.builder.cache.RoomCacheKeyBuilder;
import com.sici.chat.dao.RoomDao;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.framework.redis.batch.AbstractRedisStringCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.cache
 * @author: 20148
 * @description: 房间缓存
 * @create-date: 12/2/2024 3:05 PM
 * @version: 1.0
 */

@Component
public class RoomCache extends AbstractRedisStringCache<Integer, Room> {
    @Resource
    private RoomCacheKeyBuilder roomCacheKeyBuilder;
    @Resource
    private RoomDao roomDao;

    @Override
    public String getKey(Integer req) {
        return roomCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return 50 * 1000;
    }

    @Override
    public Map<Integer, Room> loadFromDb(List<Integer> req) {
        return roomDao.listByIds(req).stream()
                .collect(Collectors.toMap(Room::getId, room -> room));
    }
}