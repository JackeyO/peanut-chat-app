package com.sici.chat.model.chat.room.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 房间成员信息
 * @create-date: 12/12/2024 3:42 PM
 * @version: 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberCacheInfo {
    private List<Long> members;
}