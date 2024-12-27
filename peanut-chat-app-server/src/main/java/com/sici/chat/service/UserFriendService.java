package com.sici.chat.service;

import java.util.Date;

/**
* @author 20148
* @description 针对表【user_friend】的数据库操作Service
* @createDate 2024-11-25 18:13:49
*/
public interface UserFriendService  {
    Boolean checkFriendRelation(Integer userId, Integer targetId);

    void saveFriendRelation(Integer uid, Integer targetId, Date createTime);
}
