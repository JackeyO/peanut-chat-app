package com.sici.chat.model.chat.message.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description:
 * @create-date: 11/30/2024 4:14 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageVo extends CommonMessageVo {
    /**
     * 消息基本信息
     */
    private MessageVo message;
    /**
     * 消息标记信息
     */
    private MessageMarkVo messageMarkVo;
}