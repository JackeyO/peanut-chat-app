package com.sici.chat.model.chat.message.dto;

import com.sici.chat.model.chat.message.bo.MessageExtra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    private String type;
}