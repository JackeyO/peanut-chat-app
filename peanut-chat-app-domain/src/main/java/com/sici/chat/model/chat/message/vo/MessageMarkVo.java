package com.sici.chat.model.chat.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 消息标记VO
 * @create-date: 11/30/2024 4:18 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageMarkVo {
    public Integer likes;
    public Integer disLikes;
    public Integer currentUserLike;
    public Integer currentUserDisLike;
}