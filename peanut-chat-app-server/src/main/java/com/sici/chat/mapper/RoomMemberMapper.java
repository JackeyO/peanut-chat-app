package com.sici.chat.mapper;

import com.sici.chat.model.chat.room.entity.RoomMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20148
* @description 针对表【room_member】的数据库操作Mapper
* @createDate 2024-11-25 18:13:49
* @Entity com.sici.chat.model.chat.room.entity.RoomMember
*/
@Mapper
public interface RoomMemberMapper extends BaseMapper<RoomMember> {

}




