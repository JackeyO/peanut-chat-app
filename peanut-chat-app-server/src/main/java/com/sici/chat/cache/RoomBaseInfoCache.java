package com.sici.chat.cache;

import com.sici.chat.builder.cache.RoomBaseCacheRedisKeyBuilder;
import com.sici.chat.dao.RoomDao;
import com.sici.chat.model.chat.room.cache.RoomCacheInfo;
import com.sici.chat.util.ConvertBeanUtil;
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
 * @description: 房间缓存
 * @create-date: 12/2/2024 3:05 PM
 * @version: 1.0
 */

@Component
public class RoomBaseInfoCache extends AbstractRedisStringCache<Long, RoomCacheInfo> {
    @Resource
    private RoomBaseCacheRedisKeyBuilder roomBaseCacheRedisKeyBuilder;
    @Resource
    private RoomDao roomDao;

    @Override
    public String getKey(Long req) {
        return roomBaseCacheRedisKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return roomBaseCacheRedisKeyBuilder.getExpireTime().toSeconds();
    }

    @Override
    public Map<Long, RoomCacheInfo> loadFromDb(List<Long> req) {
        return roomDao.listByIds(req).stream()
                .map(room -> ConvertBeanUtil.convertSingle(room, RoomCacheInfo.class))
                .collect(Collectors.toMap(RoomCacheInfo::getId, roomCacheInfo -> roomCacheInfo));
    }
}