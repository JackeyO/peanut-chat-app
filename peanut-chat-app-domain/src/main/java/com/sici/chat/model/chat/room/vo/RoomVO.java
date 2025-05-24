package com.sici.chat.model.chat.room.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author jackey
 * @description RoomVO
 * @date 5/24/25 15:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomVO {
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

    /**
     * 房间成员数量
     */
    private Integer memberCount;

    /**
     * 房间成员
     */
    private List<Long> members;
}
