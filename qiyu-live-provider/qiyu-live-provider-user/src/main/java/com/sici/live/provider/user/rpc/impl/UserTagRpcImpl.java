package com.sici.live.provider.user.rpc.impl;

import com.sici.common.enums.user.tag.UserTagEnums;
import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.common.result.ResponseResult;
import com.sici.live.interfaces.user.rpc.IUserTagRpc;
import com.sici.live.provider.user.service.UserTagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.user.rpc.impl
 * @author: 20148
 * @description: 用户标签RPC
 * @create-date: 9/14/2024 8:29 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
@Component
@Slf4j
public class UserTagRpcImpl implements IUserTagRpc {
    @Resource
    private UserTagService userTagService;

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>设置标签")
    public ResponseResult<Boolean> setTag(Long userId, UserTagEnums userTagsEnum) {
        return userTagService.setTag(userId, userTagsEnum);
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>取消标签")
    public ResponseResult<Boolean> cancelTag(Long userId, UserTagEnums userTagsEnum) {
        return userTagService.cancelTag(userId, userTagsEnum);
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>是否包含标签")
    public ResponseResult<Boolean> containTag(Long userId, UserTagEnums userTagsEnum) {
        return userTagService.containTag(userId, userTagsEnum);
    }
}
