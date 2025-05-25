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
    AUDIO(1, "语音消息"),
    VIDEO(2, "视频消息"),
    IMAGE(3, "图片消息"),
    EMOJI(3, "表情消息"),
    NORMAL_FILE(4, "普通文件消息"),
    RECALL(5, "撤回消息"),
    SYSTEM(6, "系统通知消息"),
    CHAT_MESSAGE(7, "普通聊天消息"),
    SCAN_SUCCESS(8, "扫码成功"),
    LOGIN_SUCCESS(9, "登录成功"),
    LOGIN_QR_CODE(10, "登陆二维码信息"),
    FRIEND_APPLY(11, "好友申请信息");

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