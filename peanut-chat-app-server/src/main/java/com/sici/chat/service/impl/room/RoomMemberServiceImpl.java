package com.sici.chat.service.impl.room;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sici.chat.dao.RoomMemberDao;
import com.sici.chat.service.room.RoomMemberService;

/**
 * @author 20148
 * @description 针对表【room_member】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:50
 */
@Service
public class RoomMemberServiceImpl implements RoomMemberService {
    @Resource
    private RoomMemberDao roomMemberDao;
}
