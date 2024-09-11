package com.sici.live.user.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.common.result.ResponseResult;
import com.sici.live.model.user.dto.UserDTO;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.model.user.vo.UserVO;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.service
 * @author: 20148
 * @description: 用户服务
 * @create-date: 9/10/2024 6:42 PM
 * @version: 1.0
 */

public interface UserService extends IService<UserPO> {
    ResponseResult<UserVO> getUser(Long userId);

    ResponseResult<List<UserVO>> getUsers(List<Long> userBatchIds);

    ResponseResult removeUser(UserDTO userDTO);

    ResponseResult saveUser(UserDTO userDTO);

    ResponseResult updateUser(UserDTO userDTO);
}
