package com.sici.live.im.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.framework.redis.CacheService;
import com.sici.live.framework.rocketmq.util.RocketMqProducerUtil;
import com.sici.live.im.core.server.redis.key.ImCoreServerCacheKeyBuilder;
import com.sici.live.im.core.server.service.ImMsgAckService;
import com.sici.live.model.im.dto.ImMsgBody;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.runtime.Resources;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/19/2024 2:34 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class ImMsgAckServiceImpl implements ImMsgAckService {
    @Resource
    private ImCoreServerCacheKeyBuilder imCoreServerCacheKeyBuilder;
    @Resource
    private CacheService cacheService;
    @Resource
    private RocketMqProducerUtil rocketMqProducerUtil;

    @Override
    public void doMsgAck(ImMsgBody imMsgBody) {
        cacheService.hDelete(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsgBody.getUserId(),
                imMsgBody.getAppId()),
                imMsgBody.getMsgId());
    }
    @Override
    public void recordMsgAck(ImMsgBody imMsgBody) {
        Integer msgAckTimes = getMsgAckTimes(imMsgBody);
        if (msgAckTimes == null) msgAckTimes = -1;
        cacheService.hPut(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsgBody.getUserId(),
                imMsgBody.getAppId()),
                imMsgBody.getMsgId(),
                JSON.toJSONString(msgAckTimes + 1));
    }
    @Override
    public void sendDelayMessage(ImMsgBody imMsgBody) {
        String imMsgBodyJSON = JSON.toJSONString(imMsgBody);
        rocketMqProducerUtil.sendMessageDelay(ImMqConstant.IM_CORE_SERVER_MSG_ACK_DELAY_TOPIC,
                imMsgBodyJSON,
                2);
    }

    @Override
    public Integer getMsgAckTimes(ImMsgBody imMsgBody) {
        Object timesStr = cacheService.hGet(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsgBody.getUserId(),
                        imMsgBody.getAppId()),
                imMsgBody.getMsgId());
        if (timesStr == null) {
            return null;
        }
        if (StringUtils.isEmpty((String) timesStr)) {
            return null;
        }
        Integer times = Integer.valueOf((String) timesStr);
        return times;
    }
}
