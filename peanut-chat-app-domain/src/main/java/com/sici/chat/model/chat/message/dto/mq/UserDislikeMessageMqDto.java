package com.sici.chat.model.chat.message.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户踩消息MQ DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDislikeMessageMqDto {
    /**
     * 消息id
     */
    private Integer messageId;
    /**
     * 用户id
     */
    private Integer userId;
}
