package com.sici.chat.model.chat.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.dto
 * @author: 20148
 * @description:
 * @create-date: 11/27/2024 5:50 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
public class VideoMessageDto extends BaseFileMessageDto{
    /**
     * 总时长(以s为单位)
     */
    private Integer duration;

    /**
     * 语音消息类型
     */
    private String videoType;
}
