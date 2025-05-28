package com.sici.chat.controller.user;


import com.sici.chat.model.user.vo.UserSearchVo;
import com.sici.common.enums.code.AppHttpCodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.model.user.dto.UserProfileDto;
import com.sici.chat.model.user.vo.UserVO;
import com.sici.chat.service.user.UserService;
import com.sici.chat.util.AssertUtil;
import com.sici.common.exception.BusinessException;
import com.sici.common.result.ResponseResult;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;


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
    public ResponseResult<UserVO> profile(UserProfileDto userProfileDto) {
        // 参数校验
        AssertUtil.notNull(userProfileDto, "用户信息不能为空");
        AssertUtil.notNull(userProfileDto.getUserId(), "用户ID不能为空");

        try {
            UserVO userVO = userService.profile(userProfileDto);
            return ResponseResult.okResult(userVO);
        } catch (BusinessException e) {
            log.error("获取用户信息失败", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        }
    }

    /**
     * 按关键词搜索用户
     */

    @GetMapping("search")
    public ResponseResult<UserSearchVo> search(String keyword) {
        // 参数校验
        AssertUtil.notBlank(keyword, AppHttpCodeEnum.KEYWORDS_IS_NULL);

        try {
            UserSearchVo userVO = userService.searchUser(keyword);
            return ResponseResult.okResult(userVO);
        } catch (BusinessException e) {
            log.error("搜索用户失败", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        }
    }
}
