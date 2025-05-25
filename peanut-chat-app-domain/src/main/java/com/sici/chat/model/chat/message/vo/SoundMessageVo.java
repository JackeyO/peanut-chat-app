package com.sici.chat.model.chat.message.vo;

import lombok.Data;

/**
 * @author jackey
 * @description 语音消息
 * @date 5/25/25 15:50
 */
@Data
public class SoundMessageVo extends ChatMessageVo{
    /**
     * 语音消息的url
     */
    private String url;

    /**
     * 语音消息的时长，单位为秒
     */
    private Integer duration;

    /**
     * 语音消息的大小，单位为字节
     */
    private Long size;
}
