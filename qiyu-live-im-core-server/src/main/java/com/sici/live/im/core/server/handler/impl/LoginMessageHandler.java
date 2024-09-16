package com.sici.live.im.core.server.handler.impl;

import com.alibaba.fastjson.JSON;
import com.sici.common.enums.im.AppIdEnums;
import com.sici.common.result.ResponseResult;
import com.sici.live.im.core.server.common.ImMsg;
import com.sici.live.im.core.server.common.ImMsgBuilder;
import com.sici.live.im.core.server.handler.AbstractMessageHandler;
import com.sici.live.interfaces.im.rpc.ImTokenRpc;
import com.sici.live.model.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler.impl
 * @author: 20148
 * @description: 登录消息处理器
 * @create-date: 9/16/2024 3:24 PM
 * @version: 1.0
 */


@Component
@Slf4j
public class LoginMessageHandler implements AbstractMessageHandler {
    @DubboReference(timeout = 60000, retries = 0)
    private ImTokenRpc imTokenRpc;

    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {
        byte[] body = imMsg.getBody();
        if (body == null || body.length == 0) {
            ctx.close();
            log.error("message body is null: {}", imMsg);
            throw new IllegalArgumentException("message body is null");
        }
        ImMsgBody imMsgBody = JSON.parseObject(new String(body), ImMsgBody.class);
        String token = imMsgBody.getToken();
        if (StringUtils.isEmpty(token)) {
            ctx.close();
            log.error("token is empty");
            throw new IllegalArgumentException("token is empty");
        }

        ResponseResult<Long> userIdByToken = imTokenRpc.getUserIdByToken(token);
        Long userId = userIdByToken != null ? userIdByToken.getData() : null;

        if (userId == null) {
            ctx.close();
            log.error("userId is null");
            throw new IllegalArgumentException("userId is null");
        }

        log.info("[server received message]: " + imMsg);
        ImMsg imMsgResponse = ImMsgBuilder.buildLogin(JSON.toJSONString(
                ImMsgBody.builder()
                        .appId(AppIdEnums.QIYU_LIVE_APP.getAppId())
                        .userId(userId)
                        .token(token)
                        .data("true")
                        .build()
        ));
        ctx.writeAndFlush(imMsgResponse);
    }
}
