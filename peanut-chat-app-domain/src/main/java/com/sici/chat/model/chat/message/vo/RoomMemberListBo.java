package com.sici.chat.model.chat.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 房间成员信息
 * @create-date: 12/12/2024 3:42 PM
 * @version: 1.0
 */

@AllArgsConstructor
@Getter
@Setter
public class RoomMemberListBo {
    private List<Integer> members;
}