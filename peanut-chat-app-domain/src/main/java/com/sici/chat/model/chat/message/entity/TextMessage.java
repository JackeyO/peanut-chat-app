package com.sici.chat.model.chat.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.message.entity
 * @author: 20148
 * @description: 文本消息内容
 * @create-date: 11/25/2024 6:06 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("text_message")
public class TextMessage {
    @TableId
    private Integer id;
    /**
     * 消息id
     */
    @TableField("msg_id")
    private Integer msgId;
    /**
     * 文本内容
     */
    @TableField("content")
    private String content;
}
