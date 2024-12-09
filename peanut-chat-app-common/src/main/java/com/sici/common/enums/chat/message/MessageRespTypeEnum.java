package com.sici.common.enums.chat.message;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.channel.enums.chat.message
 * @author: 20148
 * @description: 响应消息类型枚举类
 * @create-date: 11/25/2024 5:24 PM
 * @version: 1.0
 */

public enum MessageRespTypeEnum {
    TEXT(0, "文本消息"),
    SOUND(1, "语音消息"),
    VIDEO(2, "视频消息"),
    IMAGE(3, "图片消息"),
    EMOJI(3, "表情消息"),
    RECALL(4, "撤回消息"),
    SYSTEM(5, "系统通知消息"),
    CHAT_MESSAGE(6, "普通聊天消息"),
    SCAN_SUCCESS(7, "扫码成功"),
    LOGIN_SUCCESS(8, "登录成功"),
    LOGIN_QR_CODE(9, "登陆二维码信息") ;


    private Integer type;
    private String desc;

    MessageRespTypeEnum(Integer type, String desc) {
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