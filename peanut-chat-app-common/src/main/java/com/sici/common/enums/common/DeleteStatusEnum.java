package com.sici.common.enums.common;

/**
 * @author jackey
 * @description 删除状态枚举
 * @date 5/23/25 17:45
 */
public enum DeleteStatusEnum {
    /**
     * 删除
     */
    DELETE(1, "删除"),

    /**
     * 未被删除
     */
    NOT_DELETE(0, "未被删除");

    private Integer status;
    private String desc;

    DeleteStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getstatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
