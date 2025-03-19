package com.sici.chat.service.friend;

import java.util.Date;

import com.sici.common.result.ResponseResult;

/**
 * @author 20148
 * @description 针对表【user_friend】的数据库操作Service
 * @createDate 2024-11-25 18:13:49
 */
public interface UserFriendService {
    /**
     * 检查用户是否为好友
     * 
     * @param userId   - 用户id
     * @param targetId - 目标用户id
     * @return
     */
    Boolean checkFriendRelation(Integer userId, Integer targetId);

    /**
     * 保存好友关系
     * 
     * @param uid        - 用户id
     * @param targetId   - 目标用户id
     * @param createTime - 创建时间
     */
    void saveFriendRelation(Integer uid, Integer targetId, Date createTime);

    /**
     * 获取好友列表
     * 
     * @param userId - 用户id
     * @return
     */
    ResponseResult getFriendList(Integer userId);
}
