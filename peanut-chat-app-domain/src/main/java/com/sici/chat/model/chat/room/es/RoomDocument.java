package com.sici.chat.model.chat.room.es;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房间ES文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "room_index")
@Setting(
    shards = 3,
    replicas = 1,
    refreshInterval = "30s"
)
public class RoomDocument {
    @Id
    private Long id;

    /**
     * 房间名称 - 支持模糊搜索和拼音搜索
     */
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin_analyzer", searchAnalyzer = "pinyin_analyzer")
        }
    )
    private String roomName;

    /**
     * 房间头像
     */
    @Field(type = FieldType.Keyword, index = false)
    private String avatar;

    /**
     * 房间描述 - 支持全文搜索
     */
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String description;

    /**
     * 房间类型 0:多人群，1：1对1房间
     */
    @Field(type = FieldType.Integer)
    private Integer type;

    /**
     * 扩展描述 - 支持全文搜索
     */
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String extra;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 搜索评分字段 - 根据房间活跃度、成员数量等计算
     */
    @Field(type = FieldType.Float)
    private Float searchScore;

    /**
     * 是否已删除
     */
    @Field(type = FieldType.Boolean)
    private Boolean deleted = false;

    /**
     * 房间成员数量 - 用于搜索排序
     */
    @Field(type = FieldType.Integer)
    private Integer memberCount = 0;

    /**
     * 房间最后活跃时间 - 用于搜索排序
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastActiveTime;
}