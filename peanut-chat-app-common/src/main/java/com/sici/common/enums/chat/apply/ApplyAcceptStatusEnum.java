package com.sici.common.enums.chat.apply;

/**
 * @author jackey
 * @description 好友申请处理状态枚举
 * @date 5/23/25 17:39
 */
public enum ApplyAcceptStatusEnum {
    /**
     * 0 被拒绝
     * 1 被接受
     * 2 未处理
     */
    REJECT(0, "被拒绝"),
    ACCEPT(1, "被接受"),
    UNHANDLED(2, "未处理");

    private Integer status;
    private String desc;

    ApplyAcceptStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
