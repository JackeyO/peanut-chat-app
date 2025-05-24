package com.sici.chat.service.impl.friend;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.sici.chat.builder.ImMsgBuilder;
import com.sici.chat.dao.UserFriendApplyDao;
import com.sici.chat.dao.UserFriendDao;
import com.sici.chat.model.chat.apply.entity.UserFriendApply;
import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.service.friend.UserFriendApplyService;
import com.sici.chat.service.friend.UserFriendService;
import com.sici.chat.service.ws.PushService;
import com.sici.common.enums.chat.apply.ApplyAcceptStatusEnum;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.exception.BusinessException;

import jakarta.annotation.Resource;

/**
 * @author 20148
 * @description 针对表【user_friend_apply】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Service
public class UserFriendApplyServiceImpl implements UserFriendApplyService {
    @Resource
    private UserFriendApplyDao userFriendApplyDao;
    @Resource
    private UserFriendDao userFriendDao;
    @Resource
    private PushService pushService;
    @Resource
    private UserFriendService userFriendService;

    @Override
    public void apply(UserFriendApplyDto userFriendApplyDto) {
        Long userId = userFriendApplyDto.getUserId();
        Long targetId = userFriendApplyDto.getTargetId();

        // 检查两人是否已经是好友关系
        Boolean isFriend = userFriendService.checkFriendRelation(userFriendApplyDto.getUserId(), userFriendApplyDto.getTargetId());
        if (isFriend) {
            throw new BusinessException(AppHttpCodeEnum.ALREADY_FRIEND.getCode(), AppHttpCodeEnum.ALREADY_FRIEND.getErrorMessage());
        }
        // 检查之前是否申请过
        UserFriendApply existApply = userFriendApplyDao.getUnhandledApplyByUserIdAndTargetId(userId, targetId);
        if (existApply != null) {
            throw new BusinessException(AppHttpCodeEnum.FRIEND_APPLY_EXISTS.getCode(), AppHttpCodeEnum.FRIEND_APPLY_EXISTS.getErrorMessage());
        }

        // 保存申请记录
        userFriendApplyDao.save(UserFriendApply.builder()
                .uid(userFriendApplyDto.getUserId())
                .targetId(userFriendApplyDto.getTargetId())
                .applyTime(userFriendApplyDto.getApplyTime())
                .applyMsg(userFriendApplyDto.getApplyMsg())
                .acceptStatus(ApplyAcceptStatusEnum.UNHANDLED.getStatus())
                .build());

        ImMsg imMsg = ImMsgBuilder.buildUserFriendApplyMessage(userFriendApplyDto);
        // 向目标用户推送好友申请信息
        pushService.pushMsg(imMsg, userFriendApplyDto.getTargetId());
    }

    @Override
    public String ack(Long applyId, Integer accept) {
        UserFriendApply userFriendApply = userFriendApplyDao.getById(applyId);
        if (userFriendApply == null) {
            throw new BusinessException(AppHttpCodeEnum.FRIEND_APPLY_NOT_FOUND.getCode(), AppHttpCodeEnum.FRIEND_APPLY_NOT_FOUND.getErrorMessage());
        }

        userFriendApply.setAcceptStatus(accept);

        // 更新申请信息
        userFriendApplyDao.updateById(userFriendApply);

        // 如果同意,两人就成为好友
        if (accept == 1) {
            userFriendService.saveFriendRelation(userFriendApply.getUid(), userFriendApply.getTargetId(), new Date());
            return "你们已成为好友!";
        }
        return "已拒绝该申请!";
    }
}