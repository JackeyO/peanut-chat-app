package com.sici.chat.service.impl.room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import com.sici.chat.model.chat.room.es.RoomDocument;
import com.sici.chat.repositories.es.room.RoomSearchRepository;
import com.sici.chat.service.room.RoomSearchService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 房间搜索服务
 */
@Service
@Slf4j
public class RoomSearchServiceImpl implements RoomSearchService {
    
    @Resource
    private RoomSearchRepository roomSearchRepository;
    
    /**
     * 智能搜索房间
     */
    public Page<SearchHit<RoomDocument>> searchRooms(String keyword, int page, int size) {
        log.info("智能搜索房间: keyword={}, page={}, size={}", keyword, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.intelligentSearch(keyword, pageable);
    }
    
    /**
     * 按房间名称搜索
     */
    public Page<SearchHit<RoomDocument>> searchByName(String roomName, int page, int size) {
        log.info("按房间名称搜索: roomName={}, page={}, size={}", roomName, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.searchByRoomName(roomName, pageable);
    }
    
    /**
     * 搜索热门房间
     */
    public Page<RoomDocument> getHotRooms(int minMemberCount, int page, int size) {
        log.info("搜索热门房间: minMemberCount={}, page={}, size={}", minMemberCount, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.findHotRooms(minMemberCount, pageable);
    }
    
    /**
     * 搜索活跃房间（最近24小时内有活动）
     */
    public Page<RoomDocument> getActiveRooms(int page, int size) {
        log.info("搜索活跃房间: page={}, size={}", page, size);
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String sinceTime = yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.findActiveRooms(sinceTime, pageable);
    }
    
    /**
     * 按房间类型搜索
     * @param type 0:多人群，1：1对1房间
     */
    public Page<RoomDocument> getRoomsByType(Integer type, int page, int size) {
        log.info("按房间类型搜索: type={}, page={}, size={}", type, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.searchByType(type, pageable);
    }
    
    /**
     * 搜索相似房间
     */
    public Page<RoomDocument> getSimilarRooms(Long roomId, Integer type, Integer memberCount, Float searchScore, int page, int size) {
        log.info("搜索相似房间: roomId={}, type={}, memberCount={}, searchScore={}, page={}, size={}", 
                 roomId, type, memberCount, searchScore, page, size);
        
        // 根据成员数量设定范围
        Integer minMemberCount = Math.max(0, memberCount - 10);
        Integer maxMemberCount = memberCount + 20;
        Float minScore = Math.max(0.0f, searchScore - 1.0f);
        
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.findSimilarRooms(type, minMemberCount, maxMemberCount, minScore, roomId, pageable);
    }
    
    /**
     * 高级搜索
     */
    public Page<SearchHit<RoomDocument>> advancedSearch(String keyword, Integer type, Integer minMemberCount, int page, int size) {
        log.info("高级搜索: keyword={}, type={}, minMemberCount={}, page={}, size={}", 
                 keyword, type, minMemberCount, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.advancedSearch(keyword, type, minMemberCount, pageable);
    }
    
    /**
     * 搜索新建房间（最近7天）
     */
    public Page<RoomDocument> getRecentRooms(int page, int size) {
        log.info("搜索新建房间: page={}, size={}", page, size);
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        String sinceDate = weekAgo.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.findRecentRooms(sinceDate, pageable);
    }
    
    /**
     * 搜索空房间或小房间
     */
    public Page<RoomDocument> getEmptyRooms(Integer maxMemberCount, int page, int size) {
        log.info("搜索空房间: maxMemberCount={}, page={}, size={}", maxMemberCount, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.findEmptyRooms(maxMemberCount, pageable);
    }
    
    /**
     * 按成员数量范围搜索
     */
    public Page<RoomDocument> getRoomsByMemberCount(Integer minCount, Integer maxCount, int page, int size) {
        log.info("按成员数量范围搜索: minCount={}, maxCount={}, page={}, size={}", 
                 minCount, maxCount, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.findByMemberCountRange(minCount, maxCount, pageable);
    }
    
    /**
     * 模糊搜索（容错搜索）
     */
    public Page<SearchHit<RoomDocument>> fuzzySearch(String keyword, int page, int size) {
        log.info("模糊搜索: keyword={}, page={}, size={}", keyword, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.fuzzySearch(keyword, pageable);
    }
    
    /**
     * 搜索房间名称建议词
     */
    public List<String> getRoomNameSuggestions(String prefix) {
        log.info("获取房间名称建议: prefix={}", prefix);
        return roomSearchRepository.getRoomNameSuggestions(prefix);
    }
    
    /**
     * 按描述搜索房间
     */
    public Page<SearchHit<RoomDocument>> searchByDescription(String content, int page, int size) {
        log.info("按描述搜索房间: content={}, page={}, size={}", content, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.searchByDescription(content, pageable);
    }
    
    /**
     * 按房间类型和关键词组合搜索
     */
    public Page<SearchHit<RoomDocument>> searchByKeywordAndType(String keyword, Integer type, int page, int size) {
        log.info("按关键词和类型组合搜索: keyword={}, type={}, page={}, size={}", 
                 keyword, type, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return roomSearchRepository.searchByKeywordAndType(keyword, type, pageable);
    }
    
    /**
     * 获取房间统计信息
     */
    public List<RoomDocument> getRoomStatistics() {
        log.info("获取房间统计信息");
        return roomSearchRepository.getRoomStatistics();
    }
    
    /**
     * 智能房间推荐 - 组合多种策略
     */
    public List<RoomDocument> getRecommendedRooms(Long userId, int limit) {
        log.info("获取房间推荐: userId={}, limit={}", userId, limit);
        
        // 这里可以结合用户的兴趣、加入历史等信息
        // 现在简化为返回热门+活跃+新建房间的组合
        
        try {
            // 获取热门房间
            Page<RoomDocument> hotRooms = getHotRooms(5, 0, limit / 3);
            
            // 获取活跃房间
            Page<RoomDocument> activeRooms = getActiveRooms(0, limit / 3);
            
            // 获取新建房间
            Page<RoomDocument> recentRooms = getRecentRooms(0, limit / 3);
            
            // 合并结果并去重
            return Stream.of(
                    hotRooms.getContent().stream(),
                    activeRooms.getContent().stream(),
                    recentRooms.getContent().stream()
                )
                .flatMap(stream -> stream)
                .distinct()
                .limit(limit)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            log.error("获取房间推荐失败: userId={}, limit={}", userId, limit, e);
            return List.of();
        }
    }
} 