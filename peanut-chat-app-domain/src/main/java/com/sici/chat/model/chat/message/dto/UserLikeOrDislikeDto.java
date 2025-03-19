package com.sici.chat.model.chat.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户点赞或踩请求数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeOrDislikeDto {
    /**
     * 消息id
     */
    private Integer messageId;
    /**
     * 是否点赞
     */
    private Boolean isLike;
    /**
     * 是否踩
     */
    private Boolean isDislike;
}
