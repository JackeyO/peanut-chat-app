package com.sici.chat.model.chat.message.vo;

import lombok.Data;

/**
 * @author jackey
 * @description 语音消息
 * @date 5/25/25 15:50
 */
@Data
public class NormalFileMessageVo extends ChatMessageVo{
    /**
     * 普通文件消息的url
     */
    private String url;
    /**
     * 文件消息的大小，单位为字节
     */
    private Integer size;

    /**
     * 文件消息的类型
     */
    private String type;
}
