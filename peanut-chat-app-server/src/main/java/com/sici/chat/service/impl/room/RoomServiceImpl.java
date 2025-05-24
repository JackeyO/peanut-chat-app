package com.sici.chat.service.impl.room;


import com.sici.chat.cache.GroupRoomMemberCache;
import com.sici.chat.cache.RoomCache;
import com.sici.chat.cache.TwoPersonRoomMemberCache;
import com.sici.chat.model.chat.room.cache.RoomCacheInfo;
import com.sici.chat.model.chat.room.cache.RoomMemberCacheInfo;
import com.sici.chat.model.chat.room.vo.RoomVO;
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
    private RoomCache roomCache;

    @Override
    public RoomVO getRoomInfo(Long roomId) {
        // 获取房间基本信息
        RoomCacheInfo room = roomCache.getOne(roomId);

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
}