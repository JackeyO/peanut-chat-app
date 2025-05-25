package com.sici.chat.model.chat.message.vo;

import lombok.Data;

/**
 * @author jackey
 * @description 图片消息
 * @date 5/25/25 15:50
 */
@Data
public class ImageMessageVo extends ChatMessageVo{
    /**
     * 图片消息的url
     */
    private String url;

    /**
     * 图片消息的大小，单位为字节
     */
    private Long size;
}
