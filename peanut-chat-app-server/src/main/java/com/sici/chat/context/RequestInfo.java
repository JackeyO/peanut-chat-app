package com.sici.chat.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.context
 * @author: 20148
 * @description: 请求信息
 * @create-date: 12/27/2024 5:54 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInfo {
    private Long userId;
    private String ip;
}