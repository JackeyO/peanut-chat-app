package com.sici.chat.service.chat;

import com.sici.chat.model.chat.cursor.dto.MessageCursorPageDto;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.UserLikeOrDislikeDto;
import com.sici.common.result.ResponseResult;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.service
 * @author: 20148
 * @description: 聊天业务
 * @create-date: 11/24/2024 3:35 PM
 * @version: 1.0
 */

public interface ChatService {
    ResponseResult send(MessageRequestDto messageRequestDto);
    ResponseResult messagePage(MessageCursorPageDto messageCursorPageDto);
    void likeOrDislike(UserLikeOrDislikeDto userLikeOrDislikeDto);
}