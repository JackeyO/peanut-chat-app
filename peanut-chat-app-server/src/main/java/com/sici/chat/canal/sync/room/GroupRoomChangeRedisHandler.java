package com.sici.chat.canal.sync.room;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.cache.room.RoomBaseInfoCache;
import com.sici.chat.canal.sync.AbstractDataChangeRedisHandler;
import com.sici.chat.dao.RoomDao;
import com.sici.chat.model.canal.event.DataChangeEvent;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.common.constant.canal.DatabaseConstant;
import com.sici.common.enums.chat.room.RoomTypeEnums;
import jakarta.annotation.Resource;

import java.util.Objects;

/**
 * @author jackey
 * @description
 * @date 5/29/25 18:13
 */
public class GroupRoomChangeRedisHandler extends AbstractDataChangeRedisHandler<Room, Long> {
    @Resource
    private RoomDao roomDao;
    @Resource
    private RoomBaseInfoCache roomBaseInfoCache;

    @Override
    public IService<Room> getIService() {
        return roomDao;
    }

    @Override
    public Long convertPrimaryKey(Object primaryKey) {
        return Long.valueOf(String.valueOf(primaryKey));
    }

    @Override
    public String getDatabase() {
        return DatabaseConstant.CHAT_DB_NAME;
    }

    @Override
    public String getTable() {
        return DatabaseConstant.CHAT_USER_TABLE;
    }

    @Override
    public void handlerInsertDataChangeEvent(DataChangeEvent dataChangeEvent) {
        Long roomId = convertPrimaryKey(dataChangeEvent.getPrimaryKey());
        Room room = roomDao.getById(roomId);
        if (!checkNecessary(room, dataChangeEvent)) {
            return;
        }
        roomBaseInfoCache.set(roomId, room);
    }

    @Override
    public void handlerDeleteDataChangeEvent(DataChangeEvent dataChangeEvent) {
        Long roomId = convertPrimaryKey(dataChangeEvent.getPrimaryKey());
        Room room = roomDao.getById(roomId);
        if (!checkNecessary(room, dataChangeEvent)) {
            return;
        }
        roomBaseInfoCache.deleteFromCache(roomId);
    }

    @Override
    public void handlerUpdateDataChangeEvent(DataChangeEvent dataChangeEvent) {
        Long roomId = convertPrimaryKey(dataChangeEvent.getPrimaryKey());
        Room room = roomDao.getById(roomId);
        if (!checkNecessary(room, dataChangeEvent)) {
            return;
        }
        roomBaseInfoCache.set(roomId, room);
    }

    @Override
    public Boolean checkNecessary(Room room, DataChangeEvent dataChangeEvent) {
        // 只有群聊房间变更都需要同步到Redis
        return Objects.nonNull(room) && room.getType().equals(RoomTypeEnums.GROUP.getType());
    }
}
