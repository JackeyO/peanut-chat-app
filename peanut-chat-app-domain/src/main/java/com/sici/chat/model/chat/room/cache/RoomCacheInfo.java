package com.sici.chat.model.chat.room.cache;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jackey
 * @description 房间基本信息缓存
 * @date 5/24/25 17:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCacheInfo {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间头像
     */
    private String avatar;

    /**
     * 房间描述
     */
    private String description;

    /**
     * 0未删除，1已删除
     */
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 扩展描述
     */
    private String extra;

    /**
     * 0多人群，1：1对1房间
     */
    private Integer type;
}
