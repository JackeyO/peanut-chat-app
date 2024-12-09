package com.sici.chat.model.chat.message.bo.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.bo.aggregate
 * @author: 20148
 * @description: 登陆码信息聚合参数
 * @create-date: 12/9/2024 2:45 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginQrCodeMessageAggregateParam {
    private String ticket;
    private String url;
    private Integer expireSeconds;
}