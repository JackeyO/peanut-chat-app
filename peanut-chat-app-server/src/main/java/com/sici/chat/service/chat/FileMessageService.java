package com.sici.chat.service.chat;

import com.sici.chat.model.chat.message.entity.FileMessage;

/**
 * 文件消息服务
 */
public interface FileMessageService {
    FileMessage getById(Integer id);
} 