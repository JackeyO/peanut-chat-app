package com.sici.common.enums.chat.room;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.enums.chat.room
 * @author: 20148
 * @description: 房间类型枚举类
 * @create-date: 12/2/2024 5:11 PM
 * @version: 1.0
 */

public enum RoomTypeEnums {
    TWO_PRIVATE(0, "双人私聊"),
    GROUP(1, "群聊");

    private Integer type;
    private String desc;

    RoomTypeEnums(Integer type, String desc) {
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
