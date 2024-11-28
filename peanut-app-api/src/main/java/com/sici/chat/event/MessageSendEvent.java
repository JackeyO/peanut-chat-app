package com.sici.chat.event;

import lombok.Getter;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.event
 * @author: 20148
 * @description: 消息发送事件
 * @create-date: 11/28/2024 4:09 PM
 * @version: 1.0
 */

@Getter
public class MessageSendEvent extends ApplicationEvent {
    private Integer msgId;
    public MessageSendEvent(Object source, Integer msgId) {
        super(source);
        this.msgId = msgId;
    }
}
