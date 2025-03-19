package com.sici.chat.builder;

import com.sici.chat.model.chat.friend.dto.UserFriendApplyDto;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.FriendApplyMessageVo;
import com.sici.chat.model.chat.message.vo.LoginMessageVo;
import com.sici.chat.model.chat.message.vo.LoginQrCodeMessageVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.chat.message.vo.ScanMessageVo;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.channel.builder
 * @author: 20148
 * @description: 构建ImMsg
 * @create-date: 11/30/2024 4:09 PM
 * @version: 1.0
 */

public class ImMsgBuilder {
    public static ImMsg<ChatMessageVo> buildChatMessage(ChatMessageVo messageVo) {
        ImMsg<ChatMessageVo> imMsg = new ImMsg<>();
        MessageVo message = messageVo.getMessage();
        imMsg.setType(message.getType());
        imMsg.setData(messageVo);
        return imMsg;
    }

    public static ImMsg<ScanMessageVo> buildScanMessage(ScanMessageVo scanMessageVo) {
        ImMsg<ScanMessageVo> imMsg = new ImMsg<>();
        imMsg.setType(MessageRespTypeEnum.SCAN_SUCCESS.getType());
        imMsg.setData(scanMessageVo);
        return imMsg;
    }

    public static ImMsg buildLoginMessage(LoginMessageVo loginMessageVo) {
        ImMsg<LoginMessageVo> imMsg = new ImMsg<>();
        imMsg.setType(MessageRespTypeEnum.LOGIN_SUCCESS.getType());
        imMsg.setData(loginMessageVo);
        return imMsg;
    }

    public static ImMsg buildLoginQrCodeMessage(LoginQrCodeMessageVo loginQrCodeMessageVo) {
        ImMsg<LoginQrCodeMessageVo> imMsg = new ImMsg<>();
        imMsg.setType(MessageRespTypeEnum.LOGIN_QR_CODE.getType());
        imMsg.setData(loginQrCodeMessageVo);
        return imMsg;
    }

    public static ImMsg buildUserFriendApplyMessage(UserFriendApplyDto userFriendApplyDto) {
        ImMsg<FriendApplyMessageVo> imMsg = new ImMsg<>();
        imMsg.setType(MessageRespTypeEnum.FRIEND_APPLY.getType());
        imMsg.setData(new FriendApplyMessageVo(userFriendApplyDto.getTargetId(), userFriendApplyDto.getApplyTime()));
        return imMsg;
    }
}