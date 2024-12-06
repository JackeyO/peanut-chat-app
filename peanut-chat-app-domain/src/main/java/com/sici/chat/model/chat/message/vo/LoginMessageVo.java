package com.sici.chat.model.chat.message.vo;

import com.sici.chat.model.user.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 登陆后消息
 * @create-date: 12/6/2024 2:40 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMessageVo extends CommonMessageVo{
    /**
     * 登陆成功后生成的token
     */
    private String token;
    /**
     * 登录的用户信息
     */
    private UserVO userVO;
}