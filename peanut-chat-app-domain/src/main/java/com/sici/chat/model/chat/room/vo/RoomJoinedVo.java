package com.sici.chat.model.chat.room.vo;

import com.sici.chat.model.chat.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jackey
 * @description
 * @date 5/28/25 16:13
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomJoinedVo {
    /**
     * 用户加入的房间列表(只包含房间基本信息，和群组房间，不包含私聊房间)
     */
    private List<Room>  rooms;
}
