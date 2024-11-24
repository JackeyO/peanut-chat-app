package com.sici.chat.im.core.server.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common
 * @author: 20148
 * @description: IM消息体
 * @create-date: 9/16/2024 2:57 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImMsg implements Serializable {
    // 魔法值 用于做基本安全校验
    private short magic;
    // 消息code (用于判断消息类别, 交给不同的handler去做消息处理
    //             登录消息包，登出消息包，业务消息包, 心跳消息包)
    private int code;
    // 消息体长度
    private int len;
    // 消息体的内容 以字节数组存储
    private byte[] body;
    @Override
    public String toString() {
        return "ImMsg{" +
                "magic=" + magic +
                ", code=" + code +
                ", len=" + len +
                ", body=" + new String(body) +
                '}';
    }
}
