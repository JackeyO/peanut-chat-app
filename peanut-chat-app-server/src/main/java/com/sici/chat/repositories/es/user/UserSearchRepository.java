package com.sici.chat.repositories.es.user;

import com.sici.chat.model.user.es.UserDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户搜索专用Repository
 * 职责：复杂搜索、智能匹配、搜索优化、搜索分析
 */
@Repository
public interface UserSearchRepository extends ElasticsearchRepository<UserDocument, Long> {

    // ========== 智能综合搜索 ==========
    
    /**
     * 智能综合搜索 - 支持多种匹配方式和权重
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"term": {"id": {"value": "?0", "boost": 100.0}}},
              {"term": {"nickName.keyword": {"value": "?0", "boost": 80.0}}},
              {"prefix": {"nickName.keyword": {"value": "?0", "boost": 60.0}}},
              {"multi_match": {"query": "?0", "fields": ["nickName^3", "nickName.pinyin^2"], "boost": 40.0}},
              {"multi_match": {"query": "?0", "fields": ["city^2", "province"], "boost": 20.0}},
              {"fuzzy": {"nickName": {"value": "?0", "fuzziness": "AUTO", "boost": 10.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = {
        @HighlightField(name = "nickName"),
        @HighlightField(name = "city"),
        @HighlightField(name = "province")
    })
    Page<SearchHit<UserDocument>> intelligentSearch(String keyword, Pageable pageable);

    // ========== 专项搜索方法 ==========
    
    /**
     * 昵称专项搜索 - 支持中文分词和拼音
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"match": {"nickName": {"query": "?0", "boost": 3.0}}},
              {"match": {"nickName.pinyin": {"query": "?0", "boost": 2.0}}},
              {"prefix": {"nickName.keyword": {"value": "?0", "boost": 4.0}}},
              {"term": {"nickName.keyword": {"value": "?0", "boost": 5.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = @HighlightField(name = "nickName"))
    Page<SearchHit<UserDocument>> searchByNickName(String nickName, Pageable pageable);
    
    /**
     * 拼音专项搜索
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"match": {"nickName.pinyin": {"query": "?0", "boost": 3.0}}},
              {"prefix": {"nickName.pinyin": {"value": "?0", "boost": 2.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = @HighlightField(name = "nickName"))
    Page<SearchHit<UserDocument>> searchByPinyin(String pinyin, Pageable pageable);
    
    /**
     * 地理位置搜索
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"term": {"city.keyword": {"value": "?0", "boost": 5.0}}},
              {"term": {"province.keyword": {"value": "?0", "boost": 4.0}}},
              {"match": {"city": {"query": "?0", "boost": 3.0}}},
              {"match": {"province": {"query": "?0", "boost": 2.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = {
        @HighlightField(name = "city"),
        @HighlightField(name = "province")
    })
    Page<SearchHit<UserDocument>> searchByLocation(String location, Pageable pageable);

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
                    {"match": {"nickName": "?0"}},
                    {"match": {"nickName.pinyin": "?0"}}
                  ]
                }
              }
            ],
            "filter": [
              {"term": {"deleted": false}},
              {"term": {"sex": ?1}},
              {"term": {"city.keyword": "?2"}}
            ]
          }
        }
        """)
    @Highlight(fields = @HighlightField(name = "nickName"))
    Page<SearchHit<UserDocument>> advancedSearch(String keyword, Integer sex, String city, Pageable pageable);
    
    /**
     * 相似用户推荐 - 基于某个用户的特征推荐相似用户
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"term": {"city.keyword": {"value": "?0", "boost": 3.0}}},
              {"term": {"province.keyword": {"value": "?1", "boost": 2.0}}},
              {"term": {"sex": {"value": ?2, "boost": 1.0}}}
            ],
            "filter": [
              {"term": {"deleted": false}},
              {"bool": {"must_not": {"term": {"id": ?3}}}}
            ]
          }
        }
        """)
    Page<UserDocument> findSimilarUsers(String city, String province, Integer sex, Long excludeUserId, Pageable pageable);

    // ========== 搜索统计和分析 ==========
    
    /**
     * 热门搜索词统计（这里简化，实际可能需要单独的搜索日志）
     */
    @Query("""
        {
          "aggs": {
            "popular_cities": {
              "terms": {
                "field": "city.keyword",
                "size": 10
              }
            },
            "popular_provinces": {
              "terms": {
                "field": "province.keyword",
                "size": 10
              }
            }
          },
          "size": 0
        }
        """)
    List<UserDocument> getPopularLocations();
    
    /**
     * 按关键词搜索并返回建议词
     */
    @Query("""
        {
          "suggest": {
            "nickname_suggestion": {
              "prefix": "?0",
              "completion": {
                "field": "nickName.suggest",
                "size": 10
              }
            }
          }
        }
        """)
    List<String> getSuggestions(String prefix);

    // ========== 特殊搜索场景 ==========
    
    /**
     * 范围评分搜索 - 查找评分在某个范围内的用户
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"searchScore": {"gte": ?0, "lte": ?1}}}
            ]
          }
        }
        """)
    Page<UserDocument> findBySearchScoreRange(Float minScore, Float maxScore, Pageable pageable);
    
    /**
     * 新用户搜索 - 最近注册的用户
     */
    @Query("""
        {
          "bool": {
            "filter": [
              {"term": {"deleted": false}},
              {"range": {"registerTime": {"gte": "?0"}}}
            ]
          },
          "sort": [
            {"registerTime": {"order": "desc"}},
            {"searchScore": {"order": "desc"}}
          ]
        }
        """)
    Page<UserDocument> findRecentUsers(String sinceDate, Pageable pageable);
    
    /**
     * 模糊容错搜索 - 针对输入错误的容错处理
     */
    @Query("""
        {
          "bool": {
            "should": [
              {"fuzzy": {"nickName": {"value": "?0", "fuzziness": "2", "boost": 2.0}}},
              {"wildcard": {"nickName.keyword": {"value": "*?0*", "boost": 1.0}}}
            ],
            "filter": [{"term": {"deleted": false}}],
            "minimum_should_match": 1
          }
        }
        """)
    @Highlight(fields = @HighlightField(name = "nickName"))
    Page<SearchHit<UserDocument>> fuzzySearch(String keyword, Pageable pageable);
}