package com.sici.chat.model.user.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

/**
 * 用户ES文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user_index")
@Setting(
    shards = 3,
    replicas = 1,
    refreshInterval = "30s"
)
public class UserDocument {
    @Id
    private Long id;

    /**
     * 用户openId - 精确匹配
     */
    @Field(type = FieldType.Keyword)
    private String openId;

    /**
     * 用户昵称 - 支持模糊搜索和拼音搜索
     */
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin_analyzer", searchAnalyzer = "pinyin_analyzer")
        }
    )
    private String nickName;

    /**
     * 用户头像
     */
    @Field(type = FieldType.Keyword, index = false)
    private String avatar;

    /**
     * 性别 0:男，1：女
     */
    @Field(type = FieldType.Integer)
    private Integer sex;

    /**
     * 省份 - 支持模糊搜索
     */
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String province;

    /**
     * 城市 - 支持模糊搜索
     */
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String city;

    /**
     * 国家
     */
    @Field(type = FieldType.Keyword)
    private String country;

    /**
     * 注册时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime registerTime;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updateTime;

    /**
     * 搜索评分字段 - 根据用户活跃度等计算
     */
    @Field(type = FieldType.Float)
    private Float searchScore;

    /**
     * 是否已删除
     */
    @Field(type = FieldType.Boolean)
    private Boolean deleted = false;
}