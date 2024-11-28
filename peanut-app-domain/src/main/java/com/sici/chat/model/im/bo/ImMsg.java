package com.sici.chat.model.im.bo;

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
public class ImMsg<T> implements Serializable {
    /**
     * 消息类型
     */
    Integer type;
    /**
     * 消息数据部分
     */
    T data;
    Integer fromUid;
    Integer toUid;
    Integer msgId;
}