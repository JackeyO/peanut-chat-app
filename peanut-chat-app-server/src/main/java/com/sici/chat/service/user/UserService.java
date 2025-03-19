package com.sici.chat.service.user;


import com.sici.chat.model.user.dto.UserProfileDto;
import com.sici.chat.model.user.entity.User;
import com.sici.common.result.ResponseResult;

/**
* @author 20148
* @description 针对表【user】的数据库操作Service
* @createDate 2024-12-05 17:27:51
*/
public interface UserService {

    Integer register(User user);

    String createToken(User user);

    /**
     * 用户token鉴权，鉴权成功返回登录用户信息，否则返回空
     * @param token
     * @return
     */
    User authorize(String token);

    /**
     * 获取用户信息
     * @param userProfileDto
     * @return
     */
    ResponseResult profile(UserProfileDto userProfileDto);
}
