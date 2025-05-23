package com.sici.chat.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.event
 * @author: 20148
 * @description: 用户下线事件
 * @create-date: 12/4/2024 2:46 PM
 * @version: 1.0
 */

public class UserOnlineEvent extends ApplicationEvent {
    // TODO: 定义用户事件详细信息UserEventInfo(建表来保存用户上线和下线记录)  || created by 20148 at 12/4/2024 2:54 PM
    private Long userId;
    public UserOnlineEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
