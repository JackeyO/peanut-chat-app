package com.sici.common.enums.chat.message;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.channel.enums.chat.message
 * @author: 20148
 * @description: 客户端请求消息类型
 * @create-date: 12/9/2024 2:35 PM
 * @version: 1.0
 */

public enum MessageReqTypeEnum {
    LOIN_REQUEST(0, "登录请求");
    private Integer type;
    private String desc;

    MessageReqTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
