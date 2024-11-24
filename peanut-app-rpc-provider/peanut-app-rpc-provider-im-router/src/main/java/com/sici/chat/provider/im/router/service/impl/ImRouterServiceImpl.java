package com.sici.chat.provider.im.router.service.impl;

import com.sici.common.constant.im.ImConstant;
import com.sici.common.constant.im.router.ImRouterConstant;
import com.sici.chat.interfaces.im.rpc.ImRouterHandlerRpc;
import com.sici.chat.model.im.dto.ImMsgBody;
import com.sici.chat.provider.im.router.service.ImRouterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Override
    public void sendMsg(ImMsgBody imMsg) {
        String serverBindAddress = redisCacheUtil.get(ImConstant.IM_CORE_SERVER_ADDRESS_CACHE_KEY_PREFIX +
                imMsg.getAppId() + ":" +
                imMsg.getUserId());
        if (StringUtils.isEmpty(serverBindAddress)) {
            log.error("[ImRouterServiceImpl]==>没有找到对应的serverAddress, imMsgBody:{}", imMsg);
            throw new RuntimeException("[ImRouterServiceImpl]==>没有找到对应的serverAddress, imMsgBody:" + imMsg);
        }
        RpcContext.getContext().set(ImRouterConstant.IP_ATTRIBUTE_NAME, serverBindAddress);
        imRouterHandlerRpc.sendMsg(imMsg);
    }
}
