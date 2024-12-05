package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.RoomMapper;
import com.sici.chat.model.chat.room.entity.Room;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.service.impl
 * @author: 20148
 * @description: 房间业务
 * @create-date: 11/24/2024 3:38 PM
 * @version: 1.0
 */

@Component
public class RoomDao extends ServiceImpl<RoomMapper, Room>  {
}