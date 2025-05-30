package com.sici.chat.model.canal.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author jackey
 * @description
 * @date 5/29/25 17:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataChangeEvent {
    /**
     * 数据库名
     */
    private String database;

    /**
     * 表名
     */
    private String table;

    /**
     * 操作类型
     */
    private EventType eventType;

    /**
     * 变更前数据
     */
    private Map<String, Object> beforeData;

    /**
     * 变更后数据
     */
    private Map<String, Object> afterData;

    /**
     * 主键值
     */
    private Object primaryKey;

    /**
     * 事件时间戳
     */
    private Long timestamp;

    /**
     * binlog位置信息
     */
    private String logPosition;

    public enum EventType {
        INSERT, UPDATE, DELETE
    }
}
