package com.sici.common.enums.im;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.enums.ws
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:18 PM
 * @version: 1.0
 */

public enum ImMsgTypeEnums {
    IM_MSG_LOGIN(1001, "登录IM消息包"),
    IM_MSG_LOGOUT(1002, "登出IM消息包"),
    IM_MSG_BIZ(1003, "业务IM消息包"),
    IM_MSG_ACK(1005, "IM ACK确认消息包"),
    IM_MSG_HEARTBEAT(1004, "心跳IM消息包");
    private int code;
    private String desc;

    ImMsgTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
