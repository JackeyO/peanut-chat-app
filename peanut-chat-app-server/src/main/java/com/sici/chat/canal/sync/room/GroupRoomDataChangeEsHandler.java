package com.sici.chat.canal.sync.room;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.cache.room.RoomActivityCache;
import com.sici.chat.canal.sync.AbstractDataChangeEsHandler;
import com.sici.chat.dao.RoomDao;
import com.sici.chat.model.canal.event.DataChangeEvent;
import com.sici.chat.model.chat.room.cache.RoomActivityCacheInfo;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.chat.model.chat.room.es.RoomDocument;
import com.sici.chat.repositories.es.room.RoomElasticSearchRepository;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.constant.canal.DatabaseConstant;
import com.sici.common.enums.chat.room.RoomTypeEnums;
import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * @author jackey
 * @description
 * @date 5/29/25 18:13
 */
@Component
public class GroupRoomDataChangeEsHandler extends AbstractDataChangeEsHandler<Room, RoomDocument, Long> {
    @Resource
    private RoomDao roomDao;
    @Resource
    private RoomElasticSearchRepository roomElasticSearchRepository;
    @Resource
    private RoomActivityCache roomActivityCache;

    @Override
    public RoomDocument getDoc(Room room) {
        RoomDocument roomDocument = ConvertBeanUtil.convertSingle(room, RoomDocument.class);
        roomDocument.setSearchScore(room.calculateEsSearchScore());

        RoomActivityCacheInfo roomActivityCache = this.roomActivityCache.getOne(room.getId());
        if (Objects.nonNull(roomActivityCache)) {
            roomDocument.setMemberCount(roomActivityCache.getMembersCount());
            LocalDateTime lastActivityTime = Instant.ofEpochMilli(roomActivityCache.getLastActivityTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            roomDocument.setLastActiveTime(lastActivityTime);
        }

        return roomDocument;
    }

    @Override
    public ElasticsearchRepository<RoomDocument, Long> getElasticsearchRepository() {
        return roomElasticSearchRepository;
    }

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
        return DatabaseConstant.CHAT_ROOM_TABLE;
    }

    @Override
    public Boolean checkNecessary(Room room, DataChangeEvent dataChangeEvent) {
        // 只有群聊房间变更需要同步到ES
        return Objects.nonNull(room) && room.getType().equals(RoomTypeEnums.GROUP.getType());
    }
}
