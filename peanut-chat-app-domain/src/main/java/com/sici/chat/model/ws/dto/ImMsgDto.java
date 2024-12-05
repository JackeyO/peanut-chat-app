package com.sici.chat.model.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.model.ws.dto
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:44 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImMsgDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 发送者用户id
     */
    private Long userId;
    /**
     * 发送者应用id
     */
    private Integer appId;

    /**
     * 消息类别
     */
    private Integer type;

    private String content;
    private Date createTime;
    private Date updateTime;
}
