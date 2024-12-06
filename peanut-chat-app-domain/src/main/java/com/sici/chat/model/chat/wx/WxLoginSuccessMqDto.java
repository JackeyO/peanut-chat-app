package com.sici.chat.model.chat.wx;

import com.sici.chat.model.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.wx
 * @author: 20148
 * @description: 微信扫码成功后mq消息体
 * @create-date: 12/5/2024 5:59 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginSuccessMqDto implements Serializable {
    private Integer loginCode;
    private User user;
}