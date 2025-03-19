package com.sici.chat.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户信息请求内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private Integer userId;
}
