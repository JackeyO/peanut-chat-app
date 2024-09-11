package com.sici.live.user.provider.rpc;

import com.alibaba.fastjson.JSON;
import com.sici.bean.ConvertBeanUtil;
import com.sici.common.constant.user.UserProviderConstant;
import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.common.result.ResponseResult;
import com.sici.framework.redis.CacheService;
import com.sici.live.model.user.dto.UserDTO;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.model.user.vo.UserVO;
import com.sici.live.user.interfaces.rpc.IUserRpc;
import com.sici.live.user.provider.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.runtime.Resources;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/10/2024 12:18 PM
 * @version: 1.0
 */

@DubboService
@Slf4j
@Component
public class UserRpcImpl implements IUserRpc {
    @Resource
    private UserService userService;

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>根据userId查询用户")
    public ResponseResult<UserVO> getUserById(Long userId) {
        return userService.getUser(userId);
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>根据用户id批量查询用户")
    public ResponseResult<List<UserVO>> getUserByBatchIds(List<Long> userBatchIds) {
        return userService.getUsers(userBatchIds);
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>保存用户")
    public ResponseResult saveUser(UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>删除用户")
    public ResponseResult deleteUser(UserDTO userDTO) {
        return userService.removeUser(userDTO);
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>更新用户")
    public ResponseResult updateUser(UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }
}