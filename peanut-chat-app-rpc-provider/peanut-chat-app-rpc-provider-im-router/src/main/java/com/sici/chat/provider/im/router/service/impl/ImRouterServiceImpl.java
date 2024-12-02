package com.sici.chat.provider.im.router.service.impl;

import cn.hutool.core.lang.Pair;
import cn.hutool.extra.spring.SpringUtil;
import com.sici.chat.model.im.bo.ImMsg;
import com.sici.common.constant.im.ImConstant;
import com.sici.common.constant.im.router.ImRouterConstant;
import com.sici.chat.interfaces.im.rpc.ImPushMsgRpc;
import com.sici.chat.provider.im.router.service.ImRouterService;
import com.sici.framework.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    private ImPushMsgRpc imPushMsgRpc;

    @Override
    public void routeMsg(ImMsg imMsg, Integer receiverId) {
        String serverBindAddress = RedisUtils.get(ImConstant.IM_CORE_SERVER_ADDRESS_CACHE_KEY_PREFIX +
                receiverId, String.class);
        if (StringUtils.isEmpty(serverBindAddress)) {
            log.error("[ImRouterServiceImpl]==>没有找到对应的serverAddress, imMsgBody:{}", imMsg);
            throw new RuntimeException("[ImRouterServiceImpl]==>没有找到对应的serverAddress, imMsgBody:" + imMsg);
        }
        RpcContext.getContext().set(ImRouterConstant.ADDRESS_ATTRIBUTE_NAME, serverBindAddress);
        imPushMsgRpc.pushMsg(imMsg, receiverId);
    }

    @Override
    public void routeMsg(ImMsg imMsg, List<Integer> receiverIds) {
        // 根据ip进行分组
        Map<String, ArrayList<Integer>> result = receiverIds.stream().map(receiverId ->
                        Pair.of(receiverId, RedisUtils.get(ImConstant.IM_CORE_SERVER_ADDRESS_CACHE_KEY_PREFIX + receiverId, String.class)))
                .filter(pair -> !StringUtils.isEmpty(pair.getValue()))
                .collect(Collectors.groupingBy(Pair::getValue, Collector.of(ArrayList<Integer>::new,
                        (list, pair) -> list.add(pair.getKey()), (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        })));
        // 每个IM实例分批推送
        result.forEach((serverAddress, ids) -> {
            RpcContext.getContext().set(ImRouterConstant.ADDRESS_ATTRIBUTE_NAME, serverAddress);
            imPushMsgRpc.pushMsg(imMsg, ids);
        });
    }
}