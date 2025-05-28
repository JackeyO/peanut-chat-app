package com.sici.chat.model.user.cache;

import com.sici.chat.model.chat.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jackey
 * @description
 * @date 5/28/25 16:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinedGroupRoomCacheInfo {
    /**
     * 用户加入的房间id列表(只包含房间基本信息，和群组房间，不包含私聊房间)
     */
    private List<Room> rooms;
}
