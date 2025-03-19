package com.sici.common.enums.code;

public enum AppHttpCodeEnum {
    // 成功段-2
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    // 失败
    // 登录段1~50
    NEED_LOGIN(1, "需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2, "密码错误"),
    // TOKEN50~100
    TOKEN_INVALID(50, "无效的TOKEN"),
    TOKEN_EXPIRE(51, "TOKEN已过期"),
    TOKEN_REQUIRE(52, "TOKEN是必须的"),
    // SIGN验签 100~120
    SIGN_INVALID(100, "无效的SIGN"),
    SIG_TIMEOUT(101, "SIGN已过期"),
    // 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    SERVER_ERROR(503, "服务器内部错误"),
    SERVER_FALLBACK(503, "服务降级触发，获取数据失败"),
    // 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001, "ApUser数据不存在"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    // 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000, "无权限操作"),
    NEED_ADMIND(3001, "需要管理员权限"),

    // 素材引用失效
    FRIEND_APPLY_EXISTS(504, "已经向该用户发过申请了"),
    ALREADY_FRIEND(505, "已经是好友关系"),
    USER_NOT_FOUND(506, "用户不存在"),
    BUSINESS_ERROR(507, "业务处理失败"),
    ROOM_NOT_FOUND(508, "房间不存在");

    Integer code;

    String errorMessage;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
