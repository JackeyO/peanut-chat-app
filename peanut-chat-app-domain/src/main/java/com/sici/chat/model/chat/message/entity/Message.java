package com.sici.chat.model.chat.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.sici.chat.model.chat.message.bo.MessageExtra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.model.message.entity
 * @author: 20148
 * @description: 消息实体类
 * @create-date: 11/24/2024 2:49 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message {
    @TableId
    private Integer id;
    @TableField("from_uid")
    private Integer fromUid;
    @TableField("from_uid")
    private Integer roomId;
    @TableField("reply_msg_id")
    private Integer replyMsgId;
    @TableField("type")
    private Integer type;
    @TableField("send_time")
    private Date sendTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("status")
    private Integer status;
    @TableField("extra")
    private String extra;
}