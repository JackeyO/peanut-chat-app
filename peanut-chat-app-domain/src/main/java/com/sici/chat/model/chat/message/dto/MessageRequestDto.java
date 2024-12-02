package com.sici.chat.model.chat.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.dto
 * @author: 20148
 * @description:
 * @create-date: 11/28/2024 3:20 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {
    /**
     * 消息类型
     */
    Integer type;
    Integer roomId;
    /**
     * 不同消息类型传值不同
     */
    Object body;
}