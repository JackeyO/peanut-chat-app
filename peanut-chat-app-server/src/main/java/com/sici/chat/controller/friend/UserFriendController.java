package com.sici.chat.controller.friend;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.chat.service.friend.UserFriendApplyService;
import com.sici.chat.service.friend.UserFriendService;
import com.sici.chat.util.AssertUtil;
import com.sici.common.exception.BusinessException;
import com.sici.common.result.ResponseResult;

import jakarta.annotation.Resource;
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
    public ResponseResult<String> apply(@RequestBody UserFriendApplyDto userFriendApplyDto) {
        AssertUtil.notNull(userFriendApplyDto, "好友申请信息不能为空");
        AssertUtil.notNull(userFriendApplyDto.getUserId(), "用户ID不能为空");
        AssertUtil.notNull(userFriendApplyDto.getTargetId(), "目标用户ID不能为空");
        
        try {
            userFriendApplyService.apply(userFriendApplyDto);
            return ResponseResult.okResult("好友申请已发送");
        } catch (BusinessException e) {
            log.error("发送好友申请失败", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("发送好友申请失败", e);
            return ResponseResult.errorResult(500, e.getMessage());
        }
    }

    /**
     * 确认申请信息(拒绝或接受)
     * 
     * @param applyId - 申请id
     * @param apply   - 确认状态
     * @return
     */
    @GetMapping("ack")
    public ResponseResult<String> ackApply(@RequestParam Long applyId, @RequestParam Integer apply) {
        AssertUtil.notNull(applyId, "申请ID不能为空");
        AssertUtil.notNull(apply, "确认状态不能为空");
        AssertUtil.isTrue(apply == 0 || apply == 1, "确认状态只能为0(拒绝)或1(接受)");
        
        try {
            String message = userFriendApplyService.ack(applyId, apply);
            return ResponseResult.okResult(message);
        } catch (BusinessException e) {
            log.error("处理好友申请失败", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("处理好友申请失败", e);
            return ResponseResult.errorResult(500, e.getMessage());
        }
    }

    /**
     * 获取好友列表
     * 
     * @param userId - 用户id
     * @return 好友列表
     */
    @GetMapping("list")
    public ResponseResult<List<Long>> getFriendList(Long userId) {
        // TODO: 测试该接口 created by 749291 at 2025-03-19 22:26
        AssertUtil.notNull(userId, "用户ID不能为空");
        try {
            List<Long> friendList = userFriendService.getFriendList(userId);
            return ResponseResult.okResult(friendList);
        } catch (BusinessException e) {
            log.error("获取好友列表失败", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取好友列表失败", e);
            return ResponseResult.errorResult(500, e.getMessage());
        }
    }
}
