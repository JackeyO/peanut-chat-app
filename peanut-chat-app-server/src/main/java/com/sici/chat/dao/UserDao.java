package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.UserMapper;
import com.sici.chat.model.user.entity.User;
import org.springframework.stereotype.Component;

/**
* @author 20148
* @description 针对表【user】的数据库操作Service
* @createDate 2024-12-05 17:27:51
*/
@Component
public class UserDao extends ServiceImpl<UserMapper, User> {

    /**
     * 根据openid获取用户
     * @param openId
     * @return
     */
    public User getByOpenId(String openId) {
        return lambdaQuery().eq(User::getOpenId, openId)
                .one();
    }
}
