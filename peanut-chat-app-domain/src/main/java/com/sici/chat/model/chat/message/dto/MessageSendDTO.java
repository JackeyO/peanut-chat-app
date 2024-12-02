package com.sici.chat.model.chat.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.dto
 * @author: 20148
 * @description: 消息发送传给MQ的数据
 * @create-date: 11/30/2024 3:56 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendDTO implements Serializable {
    private Integer msgId;
}