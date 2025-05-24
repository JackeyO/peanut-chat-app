package com.sici.chat.model.chat.conversation.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.room.vo.RoomVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jackey
 * @description 会话VO
 * @date 5/24/25 15:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 房间信息
     */
    private RoomVO roomVO;

    /**
     * 最后一条消息内容
     */
    private String lastMsg;

    /**
     * 未读消息数量
     */
    private Integer unreadCount;

    /**
     * 会话创建时间
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
}
