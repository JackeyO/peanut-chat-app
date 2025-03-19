package com.sici.chat.service.room;

import com.sici.common.result.ResponseResult;

/**
* @author 20148
* @description 针对表【room】的数据库操作Service
* @createDate 2024-11-25 18:13:49
*/
public interface RoomService  {
    /**
     * 根据房间id获取房间信息
     * @param roomId - 房间id
     * @return 房间信息
     */
    ResponseResult getRoomInfo(Integer roomId);
}