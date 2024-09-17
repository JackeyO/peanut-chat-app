package com.sici.live.provider.im.router.service.impl;

import com.sici.common.constant.im.router.ImRouterConstant;
import com.sici.live.interfaces.im.rpc.ImRouterHandlerRpc;
import com.sici.live.provider.im.router.service.ImRouterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.router.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 5:03 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class ImRouterServiceImpl implements ImRouterService {
    @DubboReference
    private ImRouterHandlerRpc imRouterHandlerRpc;
    @Override
    public void sendMsg(Long objectId, String imMsg) {
        String ip = "10.16.0.155:9093";
        RpcContext.getContext().set(ImRouterConstant.IP_ATTRIBUTE_NAME, ip);
        imRouterHandlerRpc.sendMsg(objectId, imMsg);
    }
}
