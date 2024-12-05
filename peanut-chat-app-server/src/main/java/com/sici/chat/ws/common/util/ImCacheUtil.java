package com.sici.chat.ws.common.util;

import com.alibaba.fastjson.JSON;
import com.sici.chat.model.ws.dto.ImMsgBody;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.qiyu.live.framework.rmq.config.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.common.util
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 4:41 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class ImCacheUtil {
    @Resource
    private MQProducer mqProducer;
    public void recordImOnlineTime(Long userId, Integer appId) {
        mqProducer.sendMsg(ImMqConstant.IM_CORE_SERVER_USER_ONLINE_CACHE_SAVE_TOPIC,
                ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build());
    }
    public void recordImBindAddressOfUserId(Long userId, Integer appId) {
        mqProducer.sendMsg(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_SAVE_TOPIC,
                ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build());
    }

    public void removeImOnlineTime(Long userId, Integer appId) {
        mqProducer.sendMsg(ImMqConstant.IM_CORE_SERVER_USER_ONLINE_CACHE_DELETE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }
    public void removeImBindAddressOfUserId(Long userId, Integer appId) {
        mqProducer.sendMsg(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_DELETE_TOPIC,
                ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build());
    }
}
