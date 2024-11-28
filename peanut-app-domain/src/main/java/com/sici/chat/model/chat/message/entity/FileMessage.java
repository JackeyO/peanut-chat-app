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
 * @description: 文件类消息(包括音频 ， 视频等)
 * @create-date: 11/25/2024 5:57 PM
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("file_message")
public class FileMessage {
    @TableId
    private Integer id;
    /**
     * 消息id
     */
    @TableField("msg_id")
    private Integer msgId;
    /**
     * 文件url地址
     */
    @TableField("url")
    private String url;
    /**
     * 文件大小
     */
    @TableField("size")
    private Long size;
    /**
     * 附加信息
     */
    @TableField("extra")
    private String extra;
    /**
     * 文件类型(1:音频，2：视频, 3:图片)
     */
    @TableField("type")
    private Integer type;
}