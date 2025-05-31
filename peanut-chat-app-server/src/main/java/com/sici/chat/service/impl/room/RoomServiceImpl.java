package com.sici.chat.service.impl.room;


import com.sici.chat.cache.room.GroupRoomMemberCache;
import com.sici.chat.cache.room.RoomBaseInfoCache;
import com.sici.chat.cache.room.TwoPersonRoomMemberCache;
import com.sici.chat.cache.user.UserJoinedGroupRoomCache;
import com.sici.chat.model.chat.room.cache.RoomMemberCacheInfo;
import com.sici.chat.model.chat.room.entity.Room;
import com.sici.chat.model.chat.room.vo.GroupRoomJoinedVo;
import com.sici.chat.model.chat.room.vo.GroupRoomSearchVo;
import com.sici.chat.model.chat.room.vo.RoomVO;
import com.sici.chat.model.user.cache.UserJoinedGroupRoomCacheInfo;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.enums.chat.room.RoomTypeEnums;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import com.sici.chat.dao.RoomDao;
import com.sici.chat.service.room.RoomService;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.exception.BusinessException;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 20148
 * @description 针对表【room】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomDao roomDao;
    @Resource
    private GroupRoomMemberCache groupRoomMemberCache;
    @Resource
    private TwoPersonRoomMemberCache twoPersonRoomMemberCache;
    @Resource
    private RoomBaseInfoCache roomBaseInfoCache;
    @Resource
    private UserJoinedGroupRoomCache userJoinedGroupRoomCache;

    @Override
    public RoomVO getRoomInfo(Long roomId) {
        // 获取房间基本信息
        Room room = roomBaseInfoCache.getOne(roomId);

        if (Objects.isNull(room)) {
            throw new BusinessException(AppHttpCodeEnum.ROOM_NOT_FOUND.getCode(), AppHttpCodeEnum.ROOM_NOT_FOUND.getErrorMessage());
        }

        RoomVO roomVO = ConvertBeanUtil.convertSingle(room, RoomVO.class);

        // 获取房间成员列表
        RoomMemberCacheInfo roomMemberCacheInfo = Optional.ofNullable(room.getType().equals(RoomTypeEnums.GROUP.getType()) ?
                        groupRoomMemberCache.getOne(roomId) : twoPersonRoomMemberCache.getOne(roomId))
                .orElse(new RoomMemberCacheInfo());
        List<Long> groupRoomMember = Optional.ofNullable(roomMemberCacheInfo.getMembers())
                .orElse(Lists.newArrayList());

        // 设置房间成员
        roomVO.setMembers(groupRoomMember);
        roomVO.setMemberCount(groupRoomMember.size());

        return roomVO;
    }

    @Override
    public GroupRoomJoinedVo getUserJoinedRooms(Long userId) {
        // 从缓存中获取用户加入的房间信息(群聊, 不包括双人聊天房间)
        UserJoinedGroupRoomCacheInfo userJoinedGroupRoomCacheInfo = userJoinedGroupRoomCache.getOne(userId);
        List<Room> rooms = userJoinedGroupRoomCacheInfo.getRooms();

        return GroupRoomJoinedVo
                .builder()
                .rooms(rooms)
                .build();
    }
}