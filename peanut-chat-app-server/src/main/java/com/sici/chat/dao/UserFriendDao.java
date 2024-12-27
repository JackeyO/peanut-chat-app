package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.UserFriendMapper;
import com.sici.chat.model.chat.friend.entity.UserFriend;
import org.springframework.stereotype.Component;

/**
 * @author 20148
 * @description 针对表【user_friend】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Component
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {

    public UserFriend getFriendRelation(Integer uid1, Integer uid2) {
        return lambdaQuery().eq(UserFriend::getUid1, uid1)
                .eq(UserFriend::getUid2, uid1)
                .eq(UserFriend::getDeleteStatus, 0)
                .one();
    }
}




