package com.sici.common.enums.chat.message;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.common.enums.chat.message
 * @author: 20148
 * @description: 消息标记类型状态枚举
 * @create-date: 12/3/2024 1:54 PM
 * @version: 1.0
 */

public enum MessageMarkActionEnums {
    YES(1, "YES"),
    NO(0, "NO");

    private Integer code;
    private String desc;

    MessageMarkActionEnums(Integer code, String desc) {
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
