package com.sici.chat.service.impl;

import com.sici.chat.dao.UserFriendDao;
import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.chat.model.chat.friend.entity.UserFriend;
import com.sici.chat.service.UserFriendService;
import com.sici.common.result.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.calcite.runtime.Resources;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    public Boolean checkFriendRelation(Integer userId, Integer targetId) {
        UserFriend userFriend = userFriendDao.getFriendRelation(userId, targetId);
        return userFriend != null;
    }

    @Override
    public void saveFriendRelation(Integer uid, Integer targetId, Date createTime) {
        userFriendDao.save(UserFriend.builder()
                .uid1(uid)
                .uid2(targetId)
                .createTime(createTime)
                .updateTime(createTime)
                .build()
        );
    }
}