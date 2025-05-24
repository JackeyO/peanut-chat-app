package com.sici.chat.service.impl.friend;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sici.chat.dao.UserFriendDao;
import com.sici.chat.model.chat.friend.entity.UserFriend;
import com.sici.chat.service.friend.UserFriendService;

import jakarta.annotation.Resource;

/**
 * @author 20148
 * @description 针对表【user_friend】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Service
public class UserFriendServiceImpl implements UserFriendService {
    @Resource
    private UserFriendDao userFriendDao;

    @Override
    public Boolean checkFriendRelation(Long userId, Long targetId) {
        UserFriend userFriend = userFriendDao.getFriendRelation(userId, targetId);
        return userFriend != null;
    }

    @Override
    public void saveFriendRelation(Long uid, Long targetId, Date createTime) {
        userFriendDao.save(UserFriend.builder()
                .uid1(uid)
                .uid2(targetId)
                .createTime(createTime)
                .updateTime(createTime)
                .build()
        );
    }

    @Override
    public List<Long> getFriendList(Long userId) {
        List<Long> friends = userFriendDao.getFriendListById(userId);
        return friends;
    }

    
}