package com.sici.chat.model.chat.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 登录二维码信息
 * @create-date: 12/9/2024 2:43 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginQrCodeMessageVo extends CommonMessageVo {
    /**
     * 二维码ticket
     */
    private String ticket;
    /**
     * 图片url
     */
    private String url;
    /**
     * 有效时间(s)
     */
    private Integer expireSeconds;
}
