package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.RoomActionMapper;
import com.sici.chat.model.chat.room.entity.RoomAction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author 20148
 * @description 针对表【room_action】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Component
public class RoomActionDao extends ServiceImpl<RoomActionMapper, RoomAction> {
}