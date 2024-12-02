package com.sici.chat.model.chat.message.bo;

import lombok.Data;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.message.bo
 * @author: 20148
 * @description: 消息附加信息类
 * @create-date: 11/25/2024 5:52 PM
 * @version: 1.0
 */

@Data
public class MessageExtra {
    /**
     * at的用户id列表
     */
    private List<Integer> atUidList;
    /**
     * 消息撤回相关信息
     */
    private MsgRecall msgRecall;
}