package com.sici.chat.repositories.es.user;

import com.sici.chat.model.user.es.UserDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author jackey
 * @description
 * @date 5/28/25 21:01
 */
@Repository
public interface UserElasticsearchRepository extends ElasticsearchRepository<UserDocument, Long> {
    // ========== 基础查询方法 ==========

    /**
     * 根据ID查找未删除的用户
     */
    Optional<UserDocument> findByIdAndDeletedFalse(Long id);

    /**
     * 根据OpenId查找用户
     */
    Optional<UserDocument> findByOpenIdAndDeletedFalse(String openId);

    /**
     * 查找所有未删除的用户（分页）
     */
    Page<UserDocument> findByDeletedFalse(Pageable pageable);

    /**
     * 根据城市查找用户
     */
    List<UserDocument> findByCityAndDeletedFalse(String city);

    /**
     * 根据省份查找用户
     */
    List<UserDocument> findByProvinceAndDeletedFalse(String province);

    /**
     * 根据性别查找用户
     */
    Page<UserDocument> findBySexAndDeletedFalse(Integer sex, Pageable pageable);

    // ========== 数据同步专用方法 ==========

    /**
     * 根据更新时间范围查询（用于增量同步）
     */
    List<UserDocument> findByUpdateTimeBetweenAndDeletedFalse(
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据创建时间范围查询（用于数据分析）
     */
    List<UserDocument> findByCreateTimeBetweenAndDeletedFalse(
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 批量查询用户是否存在
     */
    @Query("""
        {
          "terms": {
            "id": ?0
          }
        }
        """)
    List<UserDocument> findByIdIn(List<Long> ids);

    // ========== 数据统计方法 ==========

    /**
     * 统计未删除用户总数
     */
    long countByDeletedFalse();

    /**
     * 统计某个城市的用户数量
     */
    long countByCityAndDeletedFalse(String city);

    /**
     * 统计某个时间段注册的用户数量
     */
    long countByRegisterTimeBetweenAndDeletedFalse(
            LocalDateTime startTime, LocalDateTime endTime);

    // ========== 数据维护方法 ==========

    /**
     * 逻辑删除用户（标记为已删除）
     */
    @Query("""
        {
          "script": {
            "source": "ctx._source.deleted = true; ctx._source.updateTime = params.updateTime",
            "params": {
              "updateTime": "?1"
            }
          },
          "query": {
            "term": {
              "id": "?0"
            }
          }
        }
        """)
    void logicalDeleteById(Long id, LocalDateTime updateTime);

    /**
     * 批量逻辑删除
     */
    @Query("""
        {
          "script": {
            "source": "ctx._source.deleted = true; ctx._source.updateTime = params.updateTime",
            "params": {
              "updateTime": "?1"
            }
          },
          "query": {
            "terms": {
              "id": ?0
            }
          }
        }
        """)
    void logicalDeleteByIdIn(List<Long> ids, LocalDateTime updateTime);

    // ========== 简单排序查询 ==========

    /**
     * 按搜索评分排序
     */
    Page<UserDocument> findByDeletedFalseOrderBySearchScoreDesc(Pageable pageable);

    /**
     * 按注册时间排序
     */
    Page<UserDocument> findByDeletedFalseOrderByRegisterTimeDesc(Pageable pageable);

    /**
     * 按更新时间排序
     */
    Page<UserDocument> findByDeletedFalseOrderByUpdateTimeDesc(Pageable pageable);
}
