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
import com.sici.chat.util.AssertUtil;
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
        // 参数校验
        AssertUtil.notNull(messageRequestDto, "消息请求不能为空");
        AssertUtil.notNull(messageRequestDto.getType(), "消息类型不能为空");
        AssertUtil.notNull(messageRequestDto.getRoomId(), "房间ID不能为空");
        AssertUtil.notNull(messageRequestDto.getBody(), "消息内容不能为空");
        
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
        // 参数校验
        AssertUtil.notNull(messageCursorPageDto, "分页参数不能为空");
        AssertUtil.notNull(messageCursorPageDto.getRoomId(), "房间ID不能为空");
        AssertUtil.isTrue(messageCursorPageDto.getPageSize() > 0, "页大小必须大于0");
        
        return chatService.messagePage(messageCursorPageDto);
    }

    /**
     * 消息点踩处理
     */
    @GetMapping("msg/likeOrDislike")
    public void likeOrDislike(UserLikeOrDislikeDto userLikeOrDislikeDto) {
        // 参数校验
        AssertUtil.notNull(userLikeOrDislikeDto, "参数不能为空");
        AssertUtil.notNull(userLikeOrDislikeDto.getMessageId(), "消息ID不能为空");
        AssertUtil.notNull(userLikeOrDislikeDto.getIsLike(), "是否点赞不能为空");
        AssertUtil.notNull(userLikeOrDislikeDto.getIsDislike(), "是否踩不能为空");
        
        chatService.likeOrDislike(userLikeOrDislikeDto);
    }
}