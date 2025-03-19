package com.sici.chat.service.impl.room;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sici.chat.dao.RoomDao;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.chat.service.room.RoomService;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.result.ResponseResult;

/**
 * @author 20148
 * @description 针对表【room】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomDao roomDao;

    @Override
    public ResponseResult getRoomInfo(Integer roomId) {
        Room room = roomDao.getById(roomId);
        if (room == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ROOM_NOT_FOUND);
        }

        // TODO: 获取房间人数封装到RoomVo created by 749291 at 2025-03-19 22:32
        return ResponseResult.okResult(room);
    }

    
}