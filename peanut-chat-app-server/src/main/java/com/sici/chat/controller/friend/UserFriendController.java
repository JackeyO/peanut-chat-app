package com.sici.chat.controller.friend;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.chat.service.friend.UserFriendApplyService;
import com.sici.chat.service.friend.UserFriendService;
import com.sici.common.result.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.controller
 * @author: 20148
 * @description: 用户好友接口
 * @create-date: 12/23/2024 5:19 PM
 * @version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("friend")
public class UserFriendController {
    @Resource
    private UserFriendApplyService userFriendApplyService;
    @Resource
    private UserFriendService userFriendService;

    /**
     * 用户好友申请信息
     * 
     * @param userFriendApplyDto - 用户好友申请信息
     * @return - 申请结果
     */
    @PostMapping("apply")
    public ResponseResult apply(@RequestBody UserFriendApplyDto userFriendApplyDto) {
        return userFriendApplyService.apply(userFriendApplyDto);
    }

    /**
     * 确认申请信息(拒绝或接受)
     * 
     * @param applyId - 申请id
     * @param apply   - 确认状态
     * @return
     */
    @GetMapping("ack")
    public ResponseResult ackApply(@RequestParam Integer applyId, @RequestParam Integer apply) {
        return userFriendApplyService.ack(applyId, apply);
    }

    /**
     * 获取好友列表
     * 
     * @param userId - 用户id
     * @return 好友列表
     */
    @GetMapping("list")
    public ResponseResult getFriendList(Integer userId) {
        // TODO: 测试该接口 created by 749291 at 2025-03-19 22:26
        return userFriendService.getFriendList(userId);
    }
}
