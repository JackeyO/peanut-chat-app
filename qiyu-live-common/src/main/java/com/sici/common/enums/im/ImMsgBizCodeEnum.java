package com.sici.common.enums.im;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.enums.im
 * @author: 20148
 * @description: im业务消息类别枚举类
 * @create-date: 9/18/2024 5:40 PM
 * @version: 1.0
 */

public enum ImMsgBizCodeEnum {
    LIVING_ROOM_IM_CHAT_MSG_BIZ(5555, "直播间连天IM消息");

    private Integer code;
    private String desc;

    ImMsgBizCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
