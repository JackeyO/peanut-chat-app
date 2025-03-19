package com.sici.chat.service.impl.chat;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.service.ws.PushService;
import com.sici.chat.service.ws.WebSocketService;

import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.ws.router.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 5:03 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class PushServiceImpl implements PushService {
    @Resource
    private WebSocketService webSocketService;
    @Override
    public void pushMsg(ImMsg imMsg, Integer receiverId) {
        webSocketService.sendMsg(imMsg, receiverId);
    }

    @Override
    public void pushMsg(ImMsg imMsg, List<Integer> receiverIds) {
        // 根据ip进行分组
        webSocketService.sendMsg(imMsg, receiverIds);
    }
}