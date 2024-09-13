package com.sici.live.api.controller;

import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.common.result.ResponseResult;
import com.sici.live.model.user.dto.UserDTO;
import com.sici.live.interfaces.user.rpc.IUserRpc;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.api.controller
 * @author: 20148
 * @description:
 * @create-date: 9/12/2024 3:51 PM
 * @version: 1.0
 */

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @DubboReference(timeout = 60000, retries = 0)
    private IUserRpc iUserRpc;

    @Operation(summary = "保存用户信息")
    @PostMapping("save")
    @EnableInvokeLog(description = "保存用户信息")
    public ResponseResult save(@RequestBody UserDTO userDTO) {
        return iUserRpc.saveUser(userDTO);
    }

    @Operation(summary = "删除用户信息")
    @DeleteMapping("delete")
    @EnableInvokeLog(description = "删除用户信息")
    public ResponseResult delete(@RequestBody UserDTO userDTO) {
        return iUserRpc.deleteUser(userDTO);
    }
    @Operation(summary = "更新用户信息")
    @PutMapping("update")
    @EnableInvokeLog(description = "更新用户信息")
    public ResponseResult update(@RequestBody UserDTO userDTO) {
        return iUserRpc.updateUser(userDTO);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("get/{userId}")
    @EnableInvokeLog(description = "获取用户信息")
    public ResponseResult getUser(@PathVariable("userId") Long userId) {
        return iUserRpc.getUserById(userId);
    }

    @Operation(summary = "批量获取用户信息")
    @GetMapping("get")
    @EnableInvokeLog(description = "批量获取用户信息")
    public ResponseResult getUsers(@RequestParam List<Long> userIds) {
        return iUserRpc.getUserByBatchIds(userIds);
    }
}
