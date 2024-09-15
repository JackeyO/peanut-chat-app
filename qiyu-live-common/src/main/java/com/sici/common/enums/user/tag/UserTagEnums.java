package com.sici.common.enums.user.tag;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.enums.user.tag
 * @author: 20148
 * @description: 用户标签枚举类
 * @create-date: 9/14/2024 8:41 PM
 * @version: 1.0
 */

public enum UserTagEnums {
    IS_RIH_UESR(1, "是否是有钱用户", "tag_info_01"),
    IS_VIP_USER(2, "是否是VIP用户", "tag_info_02"),
    IS_OLD_USER(4, "是否是老用户", "tag_info_03");

    private long tag;
    private String desc;
    private String fieldName;

    UserTagEnums(long tag, String desc, String fieldName) {
        this.tag = tag;
        this.desc = desc;
        this.fieldName = fieldName;
    }
    public long getTag() {
        return tag;
    }
    public String getDesc() {
        return desc;
    }
    public String getFieldName() {
        return fieldName;
    }
}
