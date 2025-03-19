package com.sici.chat.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.UserFriendMapper;
import com.sici.chat.model.chat.friend.entity.UserFriend;

/**
 * @author 20148
 * @description 针对表【user_friend】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Component
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {

    /**
     * 获取好友关系
     * @param uid1 - 用户id
     * @param uid2 - 目标用户id
     * @return
     */
    public UserFriend getFriendRelation(Integer uid1, Integer uid2) {
        return lambdaQuery().eq(UserFriend::getUid1, uid1)
                .eq(UserFriend::getUid2, uid1)
                .eq(UserFriend::getDeleteStatus, 0)
                .one();
    }

    /**
     * 获取好友列表
     * @param userId - 用户id
     * @return
     */
    public List<Integer> getFriendListById(Integer userId) {
        return lambdaQuery().eq(UserFriend::getUid1, userId)
                .eq(UserFriend::getDeleteStatus, 0)
                .select(UserFriend::getUid2)
                .list()
                .stream()
                .map(UserFriend::getUid2)
                .collect(Collectors.toList());
    }
}




