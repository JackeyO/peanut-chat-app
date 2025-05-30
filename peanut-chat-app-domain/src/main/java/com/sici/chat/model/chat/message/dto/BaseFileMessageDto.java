package com.sici.chat.model.chat.message.dto;

import lombok.Data;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.dto
 * @author: 20148
 * @description:
 * @create-date: 11/27/2024 5:50 PM
 * @version: 1.0
 */

@Data
public class BaseFileMessageDto extends MessageDto{
    private String url;
    /**
     * 文件大小
     */
    private Integer size;
    private String fileType;
}