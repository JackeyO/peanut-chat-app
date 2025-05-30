package com.sici.chat.es.sync.room;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.dao.RoomDao;
import com.sici.chat.es.sync.AbstractIndexSyncService;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.chat.model.chat.room.es.RoomDocument;
import com.sici.chat.repositories.es.room.RoomElasticSearchRepository;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.enums.chat.room.RoomTypeEnums;
import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author jackey
 * @description
 * @date 5/28/25 22:46
 */
@Component
public class GroupRoomIndexSyncService extends AbstractIndexSyncService<RoomDocument, Room, Long> {
    @Resource
    private RoomDao roomDao;
    @Resource
    private RoomElasticSearchRepository roomElasticsearchRepository;

    @Override
    public String getIndexDescription() {
        return "群聊房间索引";
    }

    @Override
    public IService<Room> getIService() {
        return roomDao;
    }

    @Override
    public RoomDocument convertToDocument(Room room) {
        RoomDocument roomDocument = ConvertBeanUtil.convertSingle(room, RoomDocument.class);
        // 设置搜索评分
        roomDocument.setSearchScore(room.calculateEsSearchScore());
        return roomDocument;
    }

    @Override
    public ElasticsearchRepository<RoomDocument, Long> getElasticsearchRepository() {
        return roomElasticsearchRepository;
    }

    @Override
    public Boolean checkNecessary(Room room) {
        // 只有群聊房间需要同步到ES
        return Objects.nonNull(room) && room.getType().equals(RoomTypeEnums.GROUP.getType());
    }
}
