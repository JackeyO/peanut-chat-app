package com.sici.live.model.im.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.model.im.dto
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 4:53 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImMsgBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String msgId;
    /**
     * 接入im服务端业务线id
     */
    private Integer appId;
    /**
     * 用户id
     */
    private Long userId;
    // 从业务服务中获取，用于和im服务建立连接时用到
    /**
     * token
     */
    private String token;

    // 业务标识
    private Integer bizCode;
    /**
     * 业务数据
     */
    private String data;
}
