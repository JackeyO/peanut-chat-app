package com.sici.chat.provider.im.router.rpc;

import com.sici.chat.interfaces.im.router.rpc.ImRouterRpc;
import com.sici.chat.model.im.bo.ImMsg;
import com.sici.chat.model.im.dto.ImMsgBody;
import com.sici.chat.provider.im.router.service.ImRouterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.router.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 4:58 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
@Component
@Slf4j
public class ImRouterRpcImpl implements ImRouterRpc {
    @Resource
    private ImRouterService imRouterService;

    @Override
    public void routeMsg(ImMsg imMsg, Integer receiverId) {
    }

    @Override
    public void routeMsg(ImMsg imMsg, List<Integer> receiverIds) {

    }
}

