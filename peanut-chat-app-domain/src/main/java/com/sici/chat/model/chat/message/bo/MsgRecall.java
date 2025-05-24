package com.sici.chat.model.chat.message.bo;

import java.util.Date;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.message.bo
 * @author: 20148
 * @description: 消息撤回信息类
 * @create-date: 11/25/2024 5:54 PM
 * @version: 1.0
 */

public class MsgRecall {
    /**
     * 撤回用户id(并不一定是用户自己，也可能是管理员)
     */
    private Long recallUid;
    /**
     * 撤回时间
     */
    private Date recallTime;
}
