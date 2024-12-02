package com.sici.chat.controller;

import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.service.ChatService;
import com.sici.common.result.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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
    @PostMapping("send")
    public ResponseResult send(@RequestBody MessageRequestDto messageRequestDto) {
        return chatService.send(messageRequestDto);
    }
}