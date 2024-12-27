package com.sici.chat.model.chat.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.friend.dto
 * @author: 20148
 * @description: 用户好友申请传递参数
 * @create-date: 12/23/2024 5:20 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendApplyDto {
    /**
     * 申请发起人id
     */
    private Integer userId;
    /**
     * 申请目标id
     */
    private Integer targetId;
    /**
     * 申请信息
     */
    private String applyMsg;
    private Date applyTime;
}
