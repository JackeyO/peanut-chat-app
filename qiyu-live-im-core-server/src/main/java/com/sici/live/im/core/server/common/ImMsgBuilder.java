package com.sici.live.im.core.server.common;

import com.sici.common.constant.im.ImConstant;
import com.sici.common.enums.im.ImMsgCodeEnums;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:50 PM
 * @version: 1.0
 */

public class ImMsgBuilder {
    public static ImMsg build(short magic, int code, int len, byte[] body) {
        ImMsg imMsg = ImMsg.builder()
                .magic(magic)
                .code(code)
                .len(len)
                .body(body)
                .build();
        return imMsg;
    }
    public static ImMsg build(int code, String body) {
        ImMsg imMsg = ImMsg.builder()
                .magic(ImConstant.DEFAULT_MAGIC)
                .code(code)
                .len(body.getBytes().length)
                .body(body.getBytes())
                .build();
        return imMsg;
    }

    public static ImMsg buildLogin(String body) {
        ImMsg imMsg = ImMsg.builder()
                .magic(ImConstant.DEFAULT_MAGIC)
                .code(ImMsgCodeEnums.IM_MSG_LOGIN.getCode())
                .len(body.getBytes().length)
                .body(body.getBytes())
                .build();
        return imMsg;
    }

    public static ImMsg buildLogout(String body) {
        ImMsg imMsg = ImMsg.builder()
                .magic(ImConstant.DEFAULT_MAGIC)
                .code(ImMsgCodeEnums.IM_MSG_LOGOUT.getCode())
                .len(body.getBytes().length)
                .body(body.getBytes())
                .build();
        return imMsg;
    }

    public static ImMsg buildBiz(String body) {
        ImMsg imMsg = ImMsg.builder()
                .magic(ImConstant.DEFAULT_MAGIC)
                .code(ImMsgCodeEnums.IM_MSG_BIZ.getCode())
                .len(body.getBytes().length)
                .body(body.getBytes())
                .build();
        return imMsg;
    }
    public static ImMsg buildHeartBeat(String body) {
        ImMsg imMsg = ImMsg.builder()
                .magic(ImConstant.DEFAULT_MAGIC)
                .code(ImMsgCodeEnums.IM_MSG_HEARTBEAT.getCode())
                .len(body.getBytes().length)
                .body(body.getBytes())
                .build();
        return imMsg;
    }
}
