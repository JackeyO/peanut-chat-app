package com.sici.chat.model.chat.message.bo.aggregate;

import com.sici.chat.model.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.bo.aggregate
 * @author: 20148
 * @description: 登录消息聚合参数
 * @create-date: 12/6/2024 4:09 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMessageAggregateParam {
    /**
     * 登录的用户token
     */
    private String token;
    /**
     * 登陆的用户信息
     */
    private User user;
}