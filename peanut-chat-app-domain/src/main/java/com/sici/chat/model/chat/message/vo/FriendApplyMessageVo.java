package com.sici.chat.model.chat.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 好友申请信息
 * @create-date: 12/27/2024 5:05 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendApplyMessageVo extends CommonMessageVo{
    private Integer userId;
    private Date applyTime;
}
