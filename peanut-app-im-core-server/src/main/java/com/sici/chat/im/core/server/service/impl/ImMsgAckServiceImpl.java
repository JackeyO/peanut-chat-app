package com.sici.chat.im.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.framework.redis.RedisUtils;
import com.sici.chat.im.core.server.redis.key.ImCoreServerCacheKeyBuilder;
import com.sici.chat.im.core.server.service.ImMsgAckService;
import com.sici.chat.model.im.dto.ImMsgBody;
import com.sici.qiyu.live.framework.rmq.config.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private MQProducer mqProducer;

    @Override
    public void doMsgAck(ImMsgBody imMsgBody) {
        RedisUtils.hdel(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsgBody.getUserId(),
                        imMsgBody.getAppId()),
                imMsgBody.getMsgId());
    }
    @Override
    public void recordMsgAck(ImMsgBody imMsgBody) {
        Integer msgAckTimes = getMsgAckTimes(imMsgBody);
        if (msgAckTimes == null) msgAckTimes = -1;
        RedisUtils.hset(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsgBody.getUserId(),
                imMsgBody.getAppId()),
                imMsgBody.getMsgId(),
                JSON.toJSONString(msgAckTimes + 1));
    }
    @Override
    public void sendDelayMessage(ImMsgBody imMsgBody) {
        String imMsgBodyJSON = JSON.toJSONString(imMsgBody);
        mqProducer.sendMsg(ImMqConstant.IM_CORE_SERVER_MSG_ACK_DELAY_TOPIC,
                imMsgBody,
                2);
    }

    @Override
    public Integer getMsgAckTimes(ImMsgBody imMsgBody) {
        Object timesStr = RedisUtils.hget(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsgBody.getUserId(),
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
