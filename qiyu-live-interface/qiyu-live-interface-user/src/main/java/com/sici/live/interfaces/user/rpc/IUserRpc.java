package com.sici.live.interfaces.user.rpc;

import com.sici.common.result.ResponseResult;
import com.sici.live.model.user.dto.UserDTO;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.model.user.vo.UserVO;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.rpc.api
 * @author: 20148
 * @description: 用户rpc服务
 * @create-date: 9/9/2024 9:17 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface IUserRpc {
    ResponseResult<UserVO> getUserById(Long userId);
    ResponseResult<List<UserVO>> getUserByBatchIds(List<Long> userBatchIds);
    ResponseResult saveUser(UserDTO userDTO);
    ResponseResult deleteUser(UserDTO userDTO);
    ResponseResult updateUser(UserDTO userDTO);
}