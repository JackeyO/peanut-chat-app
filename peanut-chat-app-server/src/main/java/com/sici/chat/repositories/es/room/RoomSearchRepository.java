package com.sici.chat.repositories.es.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.sici.chat.model.chat.room.es.RoomDocument;

/**
 * 房间搜索专用Repository
 * 职责：房间搜索、智能匹配、房间推荐、搜索优化、搜索分析
 */
@Repository
public interface RoomSearchRepository extends ElasticsearchRepository<RoomDocument, Long> {

    // ========== 智能综合搜索 ==========
    
    /**
     * 智能综合搜索 - 支持多种匹配方式和权重
     * 按房间ID、房间名称、描述、扩展信息等维度搜索
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"term": {"roomName.keyword": {"value": "?0", "boost": 80.0}}},
              {"prefix": {"roomName.keyword": {"value": "?0", "boost": 70.0}}},
              {"multi_match": {"query": "?0", "fields": ["roomName^4", "roomName.pinyin^3"], "boost": 60.0}},
              {"multi_match": {"query": "?0", "fields": ["description^3", "extra^2"], "boost": 40.0}},
              {"fuzzy": {"roomName": {"value": "?0", "fuzziness": "AUTO", "boost": 20.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = {
        @HighlightField(name = "roomName"),
        @HighlightField(name = "description"),
        @HighlightField(name = "extra")
    })
    Page<SearchHit<RoomDocument>> intelligentSearch(String keyword, Pageable pageable);

    // ========== 专项搜索方法 ==========
    
    /**
     * 房间名称专项搜索 - 支持中文分词和拼音
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"match": {"roomName": {"query": "?0", "boost": 4.0}}},
              {"match": {"roomName.pinyin": {"query": "?0", "boost": 3.0}}},
              {"prefix": {"roomName.keyword": {"value": "?0", "boost": 5.0}}},
              {"term": {"roomName.keyword": {"value": "?0", "boost": 6.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = @HighlightField(name = "roomName"))
    Page<SearchHit<RoomDocument>> searchByRoomName(String roomName, Pageable pageable);
    
    /**
     * 拼音专项搜索
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"match": {"roomName.pinyin": {"query": "?0", "boost": 3.0}}},
              {"prefix": {"roomName.pinyin": {"value": "?0", "boost": 2.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = @HighlightField(name = "roomName"))
    Page<SearchHit<RoomDocument>> searchByPinyin(String pinyin, Pageable pageable);
    
    /**
     * 房间描述搜索 - 全文搜索房间描述和扩展信息
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"match": {"description": {"query": "?0", "boost": 4.0}}},
              {"match": {"extra": {"query": "?0", "boost": 3.0}}},
              {"match_phrase": {"description": {"query": "?0", "boost": 5.0}}},
              {"match_phrase": {"extra": {"query": "?0", "boost": 4.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = {
        @HighlightField(name = "description"),
        @HighlightField(name = "extra")
    })
    Page<SearchHit<RoomDocument>> searchByDescription(String content, Pageable pageable);

    /**
     * 按房间类型搜索
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"term": {"type": ?0}}
            ]
          },
          "sort": [
            {"searchScore": {"order": "desc"}},
            {"memberCount": {"order": "desc"}},
            {"lastActiveTime": {"order": "desc"}}
          ]
        }
        """)
    Page<RoomDocument> searchByType(Integer type, Pageable pageable);

    // ========== 高级搜索功能 ==========
    
    /**
     * 多条件组合搜索
     */
    @Query("""
        {
          "bool": {
            "must": [
              {
                "bool": {
                  "should": [
                    {"match": {"roomName": "?0"}},
                    {"match": {"roomName.pinyin": "?0"}},
                    {"match": {"description": "?0"}}
                  ]
                }
              }
            ],
            "filter": [
              {"term": {"deleted": false}},
              {"term": {"type": ?1}},
              {"range": {"memberCount": {"gte": ?2}}}
            ]
          }
        }
        """)
    @Highlight(fields = {
        @HighlightField(name = "roomName"),
        @HighlightField(name = "description")
    })
    Page<SearchHit<RoomDocument>> advancedSearch(String keyword, Integer type, Integer minMemberCount, Pageable pageable);
    
    /**
     * 相似房间推荐 - 基于某个房间的特征推荐相似房间
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"term": {"type": {"value": ?0, "boost": 5.0}}},
              {"range": {"memberCount": {"gte": ?1, "lte": ?2, "boost": 3.0}}},
              {"range": {"searchScore": {"gte": ?3, "boost": 2.0}}}
            ],
            "filter": [
              {"term": {"deleted": false}},
              {"bool": {"must_not": {"term": {"id": ?4}}}}
            ]
          }
        }
        """)
    Page<RoomDocument> findSimilarRooms(Integer type, Integer minMemberCount, Integer maxMemberCount, 
                                       Float minScore, Long excludeRoomId, Pageable pageable);

    // ========== 热门和推荐搜索 ==========
    
    /**
     * 热门房间搜索 - 根据成员数量和活跃度排序
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"memberCount": {"gte": ?0}}}
            ]
          },
          "sort": [
            {"searchScore": {"order": "desc"}},
            {"memberCount": {"order": "desc"}},
            {"lastActiveTime": {"order": "desc"}}
          ]
        }
        """)
    Page<RoomDocument> findHotRooms(Integer minMemberCount, Pageable pageable);
    
    /**
     * 活跃房间搜索 - 根据最后活跃时间筛选
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"lastActiveTime": {"gte": "?0"}}}
            ]
          },
          "sort": [
            {"lastActiveTime": {"order": "desc"}},
            {"searchScore": {"order": "desc"}},
            {"memberCount": {"order": "desc"}}
          ]
        }
        """)
    Page<RoomDocument> findActiveRooms(String sinceTime, Pageable pageable);

    /**
     * 新建房间搜索 - 最近创建的房间
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"createTime": {"gte": "?0"}}}
            ]
          },
          "sort": [
            {"createTime": {"order": "desc"}},
            {"searchScore": {"order": "desc"}}
          ]
        }
        """)
    Page<RoomDocument> findRecentRooms(String sinceDate, Pageable pageable);

    // ========== 搜索统计和分析 ==========
    
    /**
     * 房间类型统计
     */
    @Query("""
        {
          "aggs": {
            "room_types": {
              "terms": {
                "field": "type",
                "size": 10
              }
            },
            "member_count_stats": {
              "stats": {
                "field": "memberCount"
              }
            }
          },
          "size": 0
        }
        """)
    List<RoomDocument> getRoomStatistics();
    
    /**
     * 按关键词搜索并返回建议词
     */
    @Query("""
        {
          "suggest": {
            "room_name_suggestion": {
              "prefix": "?0",
              "completion": {
                "field": "roomName.suggest",
                "size": 10
              }
            }
          }
        }
        """)
    List<String> getRoomNameSuggestions(String prefix);

    // ========== 特殊搜索场景 ==========
    
    /**
     * 范围评分搜索 - 查找评分在某个范围内的房间
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"searchScore": {"gte": ?0, "lte": ?1}}}
            ]
          },
          "sort": [
            {"searchScore": {"order": "desc"}},
            {"memberCount": {"order": "desc"}}
          ]
        }
        """)
    Page<RoomDocument> findBySearchScoreRange(Float minScore, Float maxScore, Pageable pageable);
    
    /**
     * 成员数量范围搜索
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"memberCount": {"gte": ?0, "lte": ?1}}}
            ]
          },
          "sort": [
            {"memberCount": {"order": "desc"}},
            {"searchScore": {"order": "desc"}},
            {"lastActiveTime": {"order": "desc"}}
          ]
        }
        """)
    Page<RoomDocument> findByMemberCountRange(Integer minCount, Integer maxCount, Pageable pageable);
    
    /**
     * 模糊容错搜索 - 针对输入错误的容错处理
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"fuzzy": {"roomName": {"value": "?0", "fuzziness": "2", "boost": 3.0}}},
              {"wildcard": {"roomName.keyword": {"value": "*?0*", "boost": 2.0}}},
              {"fuzzy": {"description": {"value": "?0", "fuzziness": "1", "boost": 1.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = {
        @HighlightField(name = "roomName"),
        @HighlightField(name = "description")
    })
    Page<SearchHit<RoomDocument>> fuzzySearch(String keyword, Pageable pageable);

    /**
     * 空房间搜索 - 查找成员较少的房间
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"memberCount": {"lt": ?0}}}
            ]
          },
          "sort": [
            {"createTime": {"order": "desc"}},
            {"searchScore": {"order": "desc"}}
          ]
        }
        """)
    Page<RoomDocument> findEmptyRooms(Integer maxMemberCount, Pageable pageable);

    /**
     * 按房间类型和关键词组合搜索
     */
    @Query("""
        {
          "bool": {
            "must": [
              {
                "bool": {
                  "should": [
                    {"match": {"roomName": "?0"}},
                    {"match": {"description": "?0"}},
                    {"match": {"extra": "?0"}}
                  ]
                }
              }
            ],
            "filter": [
              {"term": {"deleted": false}},
              {"term": {"type": ?1}}
            ]
          },
          "sort": [
            {"searchScore": {"order": "desc"}},
            {"memberCount": {"order": "desc"}},
            {"lastActiveTime": {"order": "desc"}}
          ]
        }
        """)
    @Highlight(fields = {
        @HighlightField(name = "roomName"),
        @HighlightField(name = "description"),
        @HighlightField(name = "extra")
    })
    Page<SearchHit<RoomDocument>> searchByKeywordAndType(String keyword, Integer type, Pageable pageable);
}
