package com.sici.chat.controller.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.model.chat.room.es.RoomDocument;
import com.sici.chat.service.impl.room.RoomSearchServiceImpl;
import com.sici.common.result.ResponseResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 房间搜索controller
 */
@RestController
@RequestMapping("/api/room/search")
@Tag(name = "房间搜索", description = "房间搜索相关API")
@Slf4j
public class RoomSearchController {
    
    @Resource
    private RoomSearchServiceImpl roomSearchServiceImpl;
    
    /**
     * 智能搜索房间
     */
    @GetMapping("/intelligent")
    @Operation(summary = "智能搜索房间", description = "支持房间ID、名称、描述等多维度智能搜索")
    public ResponseResult<Page<SearchHit<RoomDocument>>> intelligentSearch(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<SearchHit<RoomDocument>> result = roomSearchServiceImpl.searchRooms(keyword, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 按房间名称搜索
     */
    @GetMapping("/name")
    @Operation(summary = "按房间名称搜索", description = "支持拼音搜索和模糊匹配")
    public ResponseResult<Page<SearchHit<RoomDocument>>> searchByName(
            @Parameter(description = "房间名称") @RequestParam String roomName,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<SearchHit<RoomDocument>> result = roomSearchServiceImpl.searchByName(roomName, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 搜索热门房间
     */
    @GetMapping("/hot")
    @Operation(summary = "搜索热门房间", description = "根据成员数量和活跃度推荐热门房间")
    public ResponseResult<Page<RoomDocument>> getHotRooms(
            @Parameter(description = "最少成员数") @RequestParam(defaultValue = "5") int minMemberCount,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<RoomDocument> result = roomSearchServiceImpl.getHotRooms(minMemberCount, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 搜索活跃房间
     */
    @GetMapping("/active")
    @Operation(summary = "搜索活跃房间", description = "最近24小时内有活动的房间")
    public ResponseResult<Page<RoomDocument>> getActiveRooms(
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<RoomDocument> result = roomSearchServiceImpl.getActiveRooms(page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 按房间类型搜索
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "按房间类型搜索", description = "0:多人群，1：1对1房间")
    public ResponseResult<Page<RoomDocument>> getRoomsByType(
            @Parameter(description = "房间类型") @PathVariable Integer type,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<RoomDocument> result = roomSearchServiceImpl.getRoomsByType(type, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 高级搜索
     */
    @GetMapping("/advanced")
    @Operation(summary = "高级搜索", description = "支持多条件组合搜索")
    public ResponseResult<Page<SearchHit<RoomDocument>>> advancedSearch(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "房间类型") @RequestParam(required = false) Integer type,
            @Parameter(description = "最少成员数") @RequestParam(required = false, defaultValue = "0") Integer minMemberCount,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<SearchHit<RoomDocument>> result = roomSearchServiceImpl.advancedSearch(keyword, type, minMemberCount, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 搜索相似房间
     */
    @GetMapping("/{roomId}/similar")
    @Operation(summary = "搜索相似房间", description = "基于指定房间推荐相似房间")
    public ResponseResult<Page<RoomDocument>> getSimilarRooms(
            @Parameter(description = "房间ID") @PathVariable Long roomId,
            @Parameter(description = "房间类型") @RequestParam Integer type,
            @Parameter(description = "成员数量") @RequestParam Integer memberCount,
            @Parameter(description = "搜索评分") @RequestParam Float searchScore,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<RoomDocument> result = roomSearchServiceImpl.getSimilarRooms(roomId, type, memberCount, searchScore, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 搜索新建房间
     */
    @GetMapping("/recent")
    @Operation(summary = "搜索新建房间", description = "最近7天创建的房间")
    public ResponseResult<Page<RoomDocument>> getRecentRooms(
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<RoomDocument> result = roomSearchServiceImpl.getRecentRooms(page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 搜索空房间
     */
    @GetMapping("/empty")
    @Operation(summary = "搜索空房间", description = "成员数量较少的房间")
    public ResponseResult<Page<RoomDocument>> getEmptyRooms(
            @Parameter(description = "最大成员数") @RequestParam(defaultValue = "10") Integer maxMemberCount,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<RoomDocument> result = roomSearchServiceImpl.getEmptyRooms(maxMemberCount, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 按成员数量范围搜索
     */
    @GetMapping("/members/range")
    @Operation(summary = "按成员数量范围搜索", description = "搜索指定成员数量范围的房间")
    public ResponseResult<Page<RoomDocument>> getRoomsByMemberCount(
            @Parameter(description = "最少成员数") @RequestParam Integer minCount,
            @Parameter(description = "最多成员数") @RequestParam Integer maxCount,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<RoomDocument> result = roomSearchServiceImpl.getRoomsByMemberCount(minCount, maxCount, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 模糊搜索
     */
    @GetMapping("/fuzzy")
    @Operation(summary = "模糊搜索", description = "容错搜索，适合处理拼写错误")
    public ResponseResult<Page<SearchHit<RoomDocument>>> fuzzySearch(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<SearchHit<RoomDocument>> result = roomSearchServiceImpl.fuzzySearch(keyword, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 按描述搜索
     */
    @GetMapping("/description")
    @Operation(summary = "按描述搜索", description = "全文搜索房间描述和扩展信息")
    public ResponseResult<Page<SearchHit<RoomDocument>>> searchByDescription(
            @Parameter(description = "描述内容") @RequestParam String content,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<SearchHit<RoomDocument>> result = roomSearchServiceImpl.searchByDescription(content, page, size);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 获取房间名称建议
     */
    @GetMapping("/suggestions")
    @Operation(summary = "获取房间名称建议", description = "根据输入前缀提供房间名称建议")
    public ResponseResult<List<String>> getRoomNameSuggestions(
            @Parameter(description = "输入前缀") @RequestParam String prefix) {
        
        List<String> result = roomSearchServiceImpl.getRoomNameSuggestions(prefix);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 获取房间推荐
     */
    @GetMapping("/recommend")
    @Operation(summary = "获取房间推荐", description = "智能推荐房间")
    public ResponseResult<List<RoomDocument>> getRecommendedRooms(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "推荐数量") @RequestParam(defaultValue = "20") int limit) {
        
        List<RoomDocument> result = roomSearchServiceImpl.getRecommendedRooms(userId, limit);
        return ResponseResult.okResult(result);
    }
    
    /**
     * 获取房间统计信息
     */
    @GetMapping("/statistics")
    @Operation(summary = "获取房间统计信息", description = "房间类型分布和成员数量统计")
    public ResponseResult<List<RoomDocument>> getRoomStatistics() {
        List<RoomDocument> result = roomSearchServiceImpl.getRoomStatistics();
        return ResponseResult.okResult(result);
    }
    
    /**
     * 按类型和关键词组合搜索
     */
    @GetMapping("/combo")
    @Operation(summary = "组合搜索", description = "按房间类型和关键词组合搜索")
    public ResponseResult<Page<SearchHit<RoomDocument>>> searchByKeywordAndType(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "房间类型") @RequestParam Integer type,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        
        Page<SearchHit<RoomDocument>> result = roomSearchServiceImpl.searchByKeywordAndType(keyword, type, page, size);
        return ResponseResult.okResult(result);
    }
} 