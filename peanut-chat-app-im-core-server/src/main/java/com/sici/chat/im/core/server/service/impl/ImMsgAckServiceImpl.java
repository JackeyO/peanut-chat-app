package com.sici.chat.im.core.server.service.impl;

import com.sici.chat.model.im.bo.ImMsg;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.framework.redis.RedisUtils;
import com.sici.chat.im.core.server.redis.key.ImCoreServerCacheKeyBuilder;
import com.sici.chat.im.core.server.service.ImMsgAckService;
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
    public void doMsgAck(ImMsg imMsg) {
        RedisUtils.hdel(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsg.getToUid()),
                imMsg.getMsgId());
    }

    @Override
    public void recordMsgAck(ImMsg imMsg) {
        Integer msgAckTimes = getMsgAckTimes(imMsg);
        if (msgAckTimes == null) msgAckTimes = -1;
        RedisUtils.hset(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsg.getToUid()),
                String.valueOf(imMsg.getMsgId()),
                msgAckTimes + 1);
    }

    @Override
    public void sendDelayMessage(ImMsg imMsg, int delay) {
        mqProducer.sendMsg(ImMqConstant.IM_CORE_SERVER_MSG_ACK_DELAY_TOPIC,
                imMsg,
                delay);
    }

    @Override
    public Integer getMsgAckTimes(ImMsg imMsg) {
        Object timesStr = RedisUtils.hget(imCoreServerCacheKeyBuilder.buildImMsgAckKey(imMsg.getToUid()
                ),
                String.valueOf(imMsg.getMsgId()));
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
