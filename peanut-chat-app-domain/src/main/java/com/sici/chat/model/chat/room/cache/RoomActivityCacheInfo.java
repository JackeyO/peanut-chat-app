package com.sici.chat.model.chat.room.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description 对话缓存信息
 * @date 5/25/25 12:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomActivityCacheInfo {
    /**
     * 最后活动时间
     */
    private Long lastActivityTime;
    /**
     * 成员数量
     */
    private Integer membersCount;
}
