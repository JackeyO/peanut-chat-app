package com.sici.chat.service.friend;

import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.common.result.ResponseResult;

/**
* @author 20148
* @description 针对表【user_friend_apply】的数据库操作Service
* @createDate 2024-11-25 18:13:49
*/
public interface UserFriendApplyService  {
    /**
     * 用户发送好友申请信息
     * @param userFriendApplyDto
     * @return
     */
    ResponseResult apply(UserFriendApplyDto userFriendApplyDto);

    /**
     * 确认申请信息(拒绝或接受)
     * @param applyId
     * @return
     */
    ResponseResult ack(Integer applyId, Integer accept);
}
