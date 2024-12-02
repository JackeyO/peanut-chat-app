package com.sici.chat.im.core.server.rpc;

import com.sici.chat.im.core.server.service.ImPushService;
import com.sici.chat.interfaces.im.rpc.ImPushMsgRpc;
import com.sici.chat.model.im.bo.ImMsg;
import com.sici.chat.model.im.dto.ImMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 4:55 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
@Component
@Slf4j
public class ImPushMsgRpcImpl implements ImPushMsgRpc {
    @Resource
    private ImPushService imPushService;
    @Override
    public void pushMsg(ImMsg imMsg, Integer receiverId) {
        imPushService.pushMsg(imMsg, receiverId);
    }

    @Override
    public void pushMsg(ImMsg imMsg, List<Integer> receiverIds) {
        receiverIds.forEach(receiverId -> pushMsg(imMsg, receiverId));
    }
}