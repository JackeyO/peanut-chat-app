package com.sici.chat.dao;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.RoomMemberMapper;
import com.sici.chat.model.chat.room.entity.RoomMember;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author 20148
 * @description 针对表【room_member】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Component
public class RoomMemberDao extends ServiceImpl<RoomMemberMapper, RoomMember> {
    /**
     * 获取群聊房间成员id
     *
     * @param roomId
     * @return
     */
    public Map<Long, List<Long>> getGroupRoomMember(List<Long> roomId) {
        return lambdaQuery().in(RoomMember::getRoomId, roomId).list().stream()
                .collect(Collectors.groupingBy(RoomMember::getRoomId, Collectors.mapping(RoomMember::getUid1, Collectors.toList())));
    }

    /**
     * 获取群聊房间成员id
     *
     * @param roomId
     * @return
     */
    public List<Long> getGroupRoomMember(Long roomId) {
        return lambdaQuery().eq(RoomMember::getRoomId, roomId).list()
                .stream()
                .map(RoomMember::getUid1)
                .collect(Collectors.toList());
    }

    /**
     * 获取双人聊天房间的两个成员
     *
     * @param roomId
     * @return
     */
    public List<Long> getTwoPrivateGroupMember(Long roomId) {
        RoomMember twoPersonRoomMember = lambdaQuery().eq(RoomMember::getRoomId, roomId)
                .one();
        return List.of(twoPersonRoomMember.getUid1(), twoPersonRoomMember.getUid2());
    }

    public Map<Long, List<Long>> getTwoPrivateGroupMember(List<Long> roomId) {
        return lambdaQuery().in(RoomMember::getRoomId, roomId)
                .list().stream()
                .collect(Collectors.groupingBy(RoomMember::getRoomId,
                        Collector.of(ArrayList<Long>::new, (list, roomMember) -> {
                            list.add(roomMember.getUid1());
                            list.add(roomMember.getUid2());
                        }, (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        })));
    }

    public List<Long> getRoomsByUserId(Long userId) {
        return lambdaQuery().eq(RoomMember::getUid1, userId)
                .list()
                .stream()
                .map(RoomMember::getRoomId)
                .collect(Collectors.toList());
    }
}



