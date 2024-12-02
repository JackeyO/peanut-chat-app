package com.sici.chat.model.chat.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 文本消息VO
 * @create-date: 12/2/2024 4:07 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageVo extends ChatMessageVo{
    private String content;
}