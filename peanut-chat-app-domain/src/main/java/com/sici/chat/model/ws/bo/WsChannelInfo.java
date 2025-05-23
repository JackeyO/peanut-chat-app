package com.sici.chat.model.ws.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.ws.bo
 * @author: 20148
 * @description: Channel相关标识信息
 * @create-date: 12/4/2024 12:20 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsChannelInfo {
    /**
     * 该Channel对应的用户id
     */
    private Long userId;
}
