package com.sici.chat.dao;

import com.sici.common.enums.chat.apply.ApplyAcceptStatusEnum;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.UserFriendApplyMapper;
import com.sici.chat.model.chat.apply.entity.UserFriendApply;

/**
 * @author 20148
 * @description 针对表【user_friend_apply】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Component
public class UserFriendApplyDao extends ServiceImpl<UserFriendApplyMapper, UserFriendApply> {

    /**
     * 返回未被处理的申请信息
     * @param userId
     * @param targetId
     * @return
     */
    public UserFriendApply getUnhandledApplyByUserIdAndTargetId(Long userId, Long targetId) {
        return lambdaQuery().eq(UserFriendApply::getUid, userId)
                .eq(UserFriendApply::getTargetId, targetId)
                .eq(UserFriendApply::getAcceptStatus, ApplyAcceptStatusEnum.UNHANDLED.getStatus())
                .one();
    }
}




