package com.sici.common.enums.chat.message;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.enums.chat.message
 * @author: 20148
 * @description: 消息类型枚举类
 * @create-date: 11/25/2024 5:24 PM
 * @version: 1.0
 */

public enum MessageTypeEnum {
    TEXT(0, "文本消息"),
    SOUND(1, "语音消息"),
    VIDEO(2, "视频消息"),
    IMAGE(3, "图片消息"),
    EMOJI(3, "表情消息"),
    RECALL(4, "撤回消息"),
    SYSTEM(5, "系统通知消息");

    private Integer type;
    private String desc;

    MessageTypeEnum(Integer type, String desc) {
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