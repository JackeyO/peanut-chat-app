package com.sici.chat.controller.user;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.model.user.dto.UserProfileDto;
import com.sici.chat.service.user.UserService;
import com.sici.common.result.ResponseResult;

import groovy.util.logging.Slf4j;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.controller
 * @author: 20148
 * @description: 用户模块
 * @create-date: 19/03/2025 10:45
 * @version: 1.0
 */

@RestController
@RequestMapping("peanut/chat/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("profile")
    public ResponseResult profile(UserProfileDto userProfileDto) {
        // TODO: 测试该接口 created by 749291 at 2025-03-19 22:28
        return userService.profile(userProfileDto);
    }
}
