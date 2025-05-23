package com.sici.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.chat.model.chat.friend.entity.UserFriend;
import org.apache.ibatis.annotations.Mapper;

/**
* @author jackey
* @description 针对表【user_friend】的数据库操作Mapper
* @createDate 2025-05-23 15:08:56
* @Entity com.sici.chat.model.chat.friend.entity.UserFriend
*/
@Mapper
public interface UserFriendMapper extends BaseMapper<UserFriend> {

}




