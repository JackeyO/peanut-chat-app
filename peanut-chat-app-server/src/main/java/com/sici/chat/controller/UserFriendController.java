package com.sici.chat.controller;

import com.sici.chat.model.chat.apply.entity.UserFriendApply;
import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.chat.service.UserFriendApplyService;
import com.sici.chat.service.UserFriendService;
import com.sici.common.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.runtime.Resources;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    /**
     * 用户好友申请信息
     * @param userFriendApplyDto
     * @return
     */
    @PostMapping("apply")
    public ResponseResult apply(@RequestBody UserFriendApplyDto userFriendApplyDto) {
        return userFriendApplyService.apply(userFriendApplyDto);
    }

    /**
     * 确认申请信息(拒绝或接受)
     * @param applyId
     * @return
     */
    @GetMapping("ack")
    public ResponseResult ackApply(@RequestParam Integer applyId, @RequestParam Integer apply) {
        return userFriendApplyService.ack(applyId, apply);
    }
}
