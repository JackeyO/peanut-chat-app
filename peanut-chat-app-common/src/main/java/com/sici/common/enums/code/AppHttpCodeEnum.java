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

    // 用户相关错误 4000~4100
    USER_NOT_FOUND(4000, "用户不存在"),
    USER_ALREADY_EXIST(4001, "用户已存在"),
    USER_PASSWORD_ERROR(4002, "用户密码错误"),
    USER_ACCOUNT_LOCKED(4003, "用户账号已锁定"),
    
    // 好友相关错误 4100~4200
    FRIEND_APPLY_EXISTS(4100, "已经向该用户发过申请了"),
    ALREADY_FRIEND(4101, "已经是好友关系"),
    FRIEND_APPLY_NOT_FOUND(4102, "好友申请不存在"),
    
    // 聊天消息相关错误 4200~4300
    MESSAGE_NOT_FOUND(4200, "消息不存在"),
    MESSAGE_ALREADY_LIKED(4201, "消息已点赞"),
    MESSAGE_ALREADY_DISLIKED(4202, "消息已踩"),
    
    // 房间相关错误 4300~4400
    ROOM_NOT_FOUND(4300, "房间不存在"),
    ROOM_ALREADY_EXIST(4301, "房间已存在"),
    ROOM_MEMBER_NOT_FOUND(4302, "房间成员不存在"),
    
    // 业务通用错误
    BUSINESS_ERROR(9000, "业务处理失败"),
    UNAUTHORIZED(4304, "未授权访问"),
    CHAT_FILE_NEED_ROOM_ID(4305, "聊天文件上传需要房间ID"),
    KEYWORDS_IS_NULL(4306, "关键词不能为空"),;

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
