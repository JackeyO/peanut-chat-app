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
        return ResponseResult.okResult(ConvertBeanUtil.convertSingle(userService.getUser(userId), UserVO.class));
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>根据用户id批量查询用户")
    public ResponseResult<List<UserVO>> getUserByBatchIds(List<Long> userBatchIds) {
        List<UserPO> userPOS = userService.getUsers(userBatchIds);
        return ResponseResult.okResult(ConvertBeanUtil.convertCollection(userPOS, UserVO.class));
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>保存用户")
    public ResponseResult saveUser(UserDTO userDTO) {
        boolean save = userService.save(ConvertBeanUtil.convertSingle(userDTO, UserPO.class));
        return save ? ResponseResult.okResult() : ResponseResult.errorResult();
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>删除用户")
    public ResponseResult deleteUser(UserDTO userDTO) {
        boolean removed = userService.removeById(ConvertBeanUtil.convertSingle(userDTO, UserPO.class));
        return removed ? ResponseResult.okResult() : ResponseResult.errorResult();
    }

    @Override
    @EnableInvokeLog(description = "用户RPC服务==>更新用户")
    public ResponseResult updateUser(UserDTO userDTO) {
        boolean updated = userService.updateById(ConvertBeanUtil.convertSingle(userDTO, UserPO.class));
        return updated ? ResponseResult.okResult() : ResponseResult.errorResult();
    }
}