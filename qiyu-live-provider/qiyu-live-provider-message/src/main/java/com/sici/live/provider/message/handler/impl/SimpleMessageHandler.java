package com.sici.live.provider.message.handler.impl;

import com.alibaba.fastjson.JSON;
import com.sici.common.enums.im.ImMsgBizCodeEnum;
import com.sici.live.interfaces.im.router.rpc.ImRouterRpc;
import com.sici.live.model.im.dto.ImMsgBody;
import com.sici.live.model.im.dto.ImMsgDto;
import com.sici.live.provider.message.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.message.handler.impl
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:37 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class SimpleMessageHandler implements MessageHandler {
    @DubboReference(timeout = 60000, retries = 0)
    private ImRouterRpc imRouterRpc;

    @Override
    public void handle(ImMsgBody imMsgBody) {
        ImMsgDto imMsgDto = JSON.parseObject(imMsgBody.getData(), ImMsgDto.class);
        Integer bizCode = imMsgBody.getBizCode();
        if (bizCode == null) {
            log.error("bizCode为空,无法进行消息处理, imMsgBody:{}", imMsgBody);
            throw new RuntimeException("bizCode为空,无法进行消息处理, imMsgBody:" + imMsgBody);
        }
        if (bizCode.equals(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode())) {
            // TODO: 这里制作一个演示，后续会完善直播业务  || created by 20148 at 9/18/2024 5:50 PM
            ImMsgBody imMsgBodyResponse = ImMsgBody.builder()
                    .userId(imMsgBody.getUserId())
                    .appId(imMsgBody.getAppId())
                    .bizCode(imMsgBody.getBizCode())
                    .token(imMsgBody.getToken())
                    .data(JSON.toJSONString(ImMsgDto.builder()
                            .content("直播间消息处理成功")
                            .userId(imMsgBody.getUserId())
                            .appId(imMsgDto.getAppId())
                            .build()))
                    .build();

            imRouterRpc.sendMsg(imMsgBodyResponse);
        }
    }
}
