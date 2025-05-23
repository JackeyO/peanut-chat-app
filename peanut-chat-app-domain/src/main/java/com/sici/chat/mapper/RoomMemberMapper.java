package com.sici.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.chat.model.chat.room.entity.RoomMember;
import org.apache.ibatis.annotations.Mapper;

/**
* @author jackey
* @description 针对表【room_member】的数据库操作Mapper
* @createDate 2025-05-23 15:08:56
* @Entity com.sici.chat.model.chat.room.entity.RoomMember
*/
@Mapper
public interface RoomMemberMapper extends BaseMapper<RoomMember> {

}




