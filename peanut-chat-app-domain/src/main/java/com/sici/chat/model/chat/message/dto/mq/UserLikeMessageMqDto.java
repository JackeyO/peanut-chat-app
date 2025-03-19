package com.sici.chat.model.chat.message.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户点赞消息MQ DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeMessageMqDto {
    /**
     * 消息id
     */
    private Integer messageId;
    /**
     * 用户id
     */
    private Integer userId;
}
