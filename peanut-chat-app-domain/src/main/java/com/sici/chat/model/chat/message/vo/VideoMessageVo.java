package com.sici.chat.model.chat.message.vo;

import lombok.Data;

/**
 * @author jackey
 * @description 视频消息
 * @date 5/25/25 15:50
 */
@Data
public class VideoMessageVo extends ChatMessageVo{
    /**
     * 视频消息的url
     */
    private String url;

    /**
     * 视频消息的时长，单位为秒
     */
    private Integer duration;

    /**
     * 视频消息的大小，单位为字节
     */
    private Integer size;

    /**
     * 视频消息的类型
     */
    private String type;
}
