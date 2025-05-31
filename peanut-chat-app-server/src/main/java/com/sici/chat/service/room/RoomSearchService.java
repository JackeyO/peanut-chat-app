package com.sici.chat.service.room;

import com.sici.chat.model.chat.room.es.RoomDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

/**
 * @author jackey
 * @description
 * @date 6/1/25 11:55
 */
public interface RoomSearchService {
    /**
     * 智能搜索房间
     */
    Page<SearchHit<RoomDocument>> searchRooms(String keyword, int page, int size);

    /**
     * 按房间名称搜索
     */
    Page<SearchHit<RoomDocument>> searchByName(String roomName, int page, int size);


    /**
     * 搜索热门房间
     */
    Page<RoomDocument> getHotRooms(int minMemberCount, int page, int size) ;


    /**
     * 搜索活跃房间（最近24小时内有活动）
     */
    Page<RoomDocument> getActiveRooms(int page, int size);;

    /**
     * 按房间类型搜索
     * @param type 0:多人群，1：1对1房间
     */
    Page<RoomDocument> getRoomsByType(Integer type, int page, int size);

    /**
     * 搜索相似房间
     */
    Page<RoomDocument> getSimilarRooms(Long roomId, Integer type, Integer memberCount, Float searchScore, int page, int size);

    /**
     * 高级搜索
     */
    Page<SearchHit<RoomDocument>> advancedSearch(String keyword, Integer type, Integer minMemberCount, int page, int size);

    /**
     * 搜索新建房间（最近7天）
     */
    Page<RoomDocument> getRecentRooms(int page, int size);

    /**
     * 搜索空房间或小房间
     */
    Page<RoomDocument> getEmptyRooms(Integer maxMemberCount, int page, int size);


    /**
     * 按成员数量范围搜索
     */
    Page<RoomDocument> getRoomsByMemberCount(Integer minCount, Integer maxCount, int page, int size);


    /**
     * 模糊搜索（容错搜索）
     */
    Page<SearchHit<RoomDocument>> fuzzySearch(String keyword, int page, int size);


    /**
     * 搜索房间名称建议词
     */
    List<String> getRoomNameSuggestions(String prefix);


    /**
     * 按描述搜索房间
     */
    Page<SearchHit<RoomDocument>> searchByDescription(String content, int page, int size);


    /**
     * 按房间类型和关键词组合搜索
     */
    Page<SearchHit<RoomDocument>> searchByKeywordAndType(String keyword, Integer type, int page, int size);


    /**
     * 获取房间统计信息
     */
    List<RoomDocument> getRoomStatistics();

    /**
     * 智能房间推荐 - 组合多种策略
     */
    List<RoomDocument> getRecommendedRooms(Long userId, int limit);
}
