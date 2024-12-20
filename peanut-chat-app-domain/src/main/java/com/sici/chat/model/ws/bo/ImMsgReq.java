package com.sici.chat.model.ws.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.ws.bo
 * @author: 20148
 * @description: IM请求包
 * @create-date: 12/19/2024 5:21 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImMsgReq<T> {
    /**
     * 请求类型
     */
    private Integer type;
    /**
     * 请求数据包
     */
    private T data;
}
