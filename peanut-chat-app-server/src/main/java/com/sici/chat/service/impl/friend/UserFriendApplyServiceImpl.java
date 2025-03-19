package com.sici.chat.service.impl.friend;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sici.chat.asser.AssertUtil;
import com.sici.chat.builder.ImMsgBuilder;
import com.sici.chat.dao.UserFriendApplyDao;
import com.sici.chat.dao.UserFriendDao;
import com.sici.chat.model.chat.apply.entity.UserFriendApply;
import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.service.friend.UserFriendApplyService;
import com.sici.chat.service.friend.UserFriendService;
import com.sici.chat.service.ws.PushService;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.result.ResponseResult;

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
    public ResponseResult apply(UserFriendApplyDto userFriendApplyDto) {
        Integer userId = userFriendApplyDto.getUserId();
        Integer targetId = userFriendApplyDto.getTargetId();

        // 检查两人是否已经是好友关系
        Boolean isFriend = userFriendService.checkFriendRelation(userFriendApplyDto.getUserId(), userFriendApplyDto.getTargetId());
        if (isFriend) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ALREADY_FRIEND);
        }
        // 检查之前是否申请过
        UserFriendApply existApply = userFriendApplyDao.getByUserIdAndTargetId(userId, targetId);
        if (existApply != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FRIEND_APPLY_EXISTS);
        }

        // 保存申请记录
        userFriendApplyDao.save(UserFriendApply.builder()
                .uid(userFriendApplyDto.getUserId())
                .targetId(userFriendApplyDto.getTargetId())
                .applyTime(userFriendApplyDto.getApplyTime())
                .applyMsg(userFriendApplyDto.getApplyMsg())
                .build());

        ImMsg imMsg = ImMsgBuilder.buildUserFriendApplyMessage(userFriendApplyDto);
        // 向目标用户推送好友申请信息
        pushService.pushMsg(imMsg, userFriendApplyDto.getTargetId());
        return ResponseResult.okResult("好友申请已发送");
    }

    @Override
    public ResponseResult ack(Integer applyId, Integer accept) {
        UserFriendApply userFriendApply = userFriendApplyDao.getById(applyId);
        AssertUtil.isNotEmpty(userFriendApply, "申请不存在");

        userFriendApply.setDealStatus(1);
        userFriendApply.setAcceptStatus(accept);

        // 更新申请信息
        userFriendApplyDao.updateById(userFriendApply);

        // 如果同意,两人就成为好友
        if (accept == 1) {
            userFriendService.saveFriendRelation(userFriendApply.getUid(), userFriendApply.getTargetId(), new Date());
            return ResponseResult.okResult("你们已成为好友!");
        }
        return ResponseResult.okResult("已拒绝该申请!");
    }
}