package com.sici.chat.controller.chat;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.model.chat.cursor.dto.MessageCursorPageDto;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.dto.UserLikeOrDislikeDto;
import com.sici.chat.service.chat.ChatService;
import com.sici.common.result.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.controller
 * @author: 20148
 * @description: 聊天相关接口
 * @create-date: 11/20/2024 6:18 PM
 * @version: 1.0
 */

@RestController
@RequestMapping("peanut/chat")
@Slf4j
public class ChatController {
    @Resource
    private ChatService chatService;

    /**
     * 发送消息
     * 
     * @param messageRequestDto
     * @return
     */
    @PostMapping("send")
    public ResponseResult send(@RequestBody MessageRequestDto messageRequestDto) {
        return chatService.send(messageRequestDto);
    }

    /**
     * 获取聊天记录
     * 
     * @param messageCursorPageDto
     * @return
     */
    @PostMapping("msg/page")
    public ResponseResult messagePage(@RequestBody MessageCursorPageDto messageCursorPageDto) {
        return chatService.messagePage(messageCursorPageDto);
    }

    /**
     * 消息点踩处理
     */
    @GetMapping("msg/likeOrDislike")
    public void likeOrDislike(UserLikeOrDislikeDto userLikeOrDislikeDto) {
        // TODO: 完成该接口后续并测试该接口 created by 749291 at 2025-03-19 22:30
        chatService.likeOrDislike(userLikeOrDislikeDto);
    }
}