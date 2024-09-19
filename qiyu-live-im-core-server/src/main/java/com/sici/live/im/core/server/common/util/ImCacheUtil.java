package com.sici.live.im.core.server.common.util;

import com.alibaba.fastjson.JSON;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.live.framework.rocketmq.util.RocketMqProducerUtil;
import com.sici.live.model.im.dto.ImMsgBody;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common.util
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 4:41 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class ImCacheUtil {
    @Resource
    private RocketMqProducerUtil rocketMqProducerUtil;
    public void recordImOnlineTime(Long userId, Integer appId) {
        rocketMqProducerUtil.sendMessage(ImMqConstant.IM_CORE_SERVER_USER_ONLINE_CACHE_SAVE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }
    public void recordImBindAddressOfUserId(Long userId, Integer appId) {
        rocketMqProducerUtil.sendMessage(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_SAVE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }

    public void removeImOnlineTime(Long userId, Integer appId) {
        rocketMqProducerUtil.sendMessage(ImMqConstant.IM_CORE_SERVER_USER_ONLINE_CACHE_DELETE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }
    public void removeImBindAddressOfUserId(Long userId, Integer appId) {
        rocketMqProducerUtil.sendMessage(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_DELETE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }
}
