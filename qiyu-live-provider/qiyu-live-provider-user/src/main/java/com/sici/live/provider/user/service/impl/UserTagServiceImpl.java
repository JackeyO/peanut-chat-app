package com.sici.live.provider.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.common.ReflectUtils;
import com.sici.common.constant.user.UserProviderConstant;
import com.sici.common.constant.user.tag.UserTagConstant;
import com.sici.common.enums.user.tag.UserTagEnums;
import com.sici.common.result.ResponseResult;
import com.sici.framework.redis.CacheService;
import com.sici.live.model.user.tag.pojo.UserTagPO;
import com.sici.live.model.user.tag.vo.UserTagVO;
import com.sici.live.provider.user.mapper.UserTagMapper;
import com.sici.live.provider.user.redis.key.UserProviderCacheKeyBuilder;
import com.sici.live.provider.user.service.UserTagService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.runtime.Resources;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.user.service.impl
 * @author: 20148
 * @description: 用户标签服务实现
 * @create-date: 9/14/2024 8:34 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTagPO> implements UserTagService {
    @Resource
    private UserTagMapper userTagMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private CacheService cacheService;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Resource
    private MQProducer mqProducer;


    @Override
    public ResponseResult<Boolean> setTag(Long userId, UserTagEnums userTagsEnum) {
        int effected = userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag());
        if (effected == 0) {
            UserTagPO userTag = getById(userId);
            if (userTag == null) {
                String lockResult = (String) redisTemplate.execute((RedisCallback) (connection) -> {
                    RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                    RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                    String result = (String) connection.execute("set", keySerializer.serialize(userProviderCacheKeyBuilder.buildUserTakLockKey(userId)),
                            valueSerializer.serialize(-1),
                            "NX".getBytes(StandardCharsets.UTF_8),
                            "EX".getBytes(StandardCharsets.UTF_8),
                            UserTagConstant.USER_TAG_LOCK_KEY_EXPIRE.toString().getBytes(StandardCharsets.UTF_8));
                    return result;
                });
                if (lockResult.equals("OK")) {
                    save(UserTagPO.builder()
                            .userId(userId)
                            .build());
                    effected = userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag());
                }
            }
        }

        // 如果成功更新，清理缓存
        if (effected > 0) removeCache(userId);
        return ResponseResult.okResult(effected > 0);
    }

    @Override
    public ResponseResult<Boolean> cancelTag(Long userId, UserTagEnums userTagsEnum) {
        int effected = userTagMapper.cancelTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag());
        // 如果成功取消，清理缓存
        if (effected > 0) removeCache(userId);
        return ResponseResult.okResult(effected > 0);
    }

    @Override
    public ResponseResult<Boolean> containTag(Long userId, UserTagEnums userTagsEnum) {
        // 从缓存中查询
        UserTagVO userTagVO = getFromCache(userId);
        if (userTagVO == null) {
            userTagVO = new UserTagVO();
            UserTagPO userTagPO = getById(userId);
            BeanUtils.copyProperties(userTagPO, userTagVO);

            // 保存到缓存
            saveToCache(userTagVO);
        }

        Long tagInfo = (Long) ReflectUtils.getPropertie(userTagVO, UserTagConstant.TAG_INFO_ATTRIBUTE_NAME_MAP.get(
                userTagsEnum.getFieldName()
        ));

        Boolean contains = (tagInfo & userTagsEnum.getTag()) != 0;

        return ResponseResult.okResult(contains);
    }

    private void removeCache(Long userId) {
        cacheService.delete(userProviderCacheKeyBuilder.buildUserTakKey(userId));
        Message message = new Message();
        message.setTopic(UserProviderConstant.USER_TAG_CACHE_DELETE_TOPIC);
        message.setDelayTimeLevel(1);
        message.setBody(JSON.toJSONString(userId).getBytes());

        // 发送消息,延迟删除
        try {
            mqProducer.send(message);
        } catch (Exception e) {
            log.info("用户标签服务==>发送用户标签删除消息==>失败");
            throw new RuntimeException("用户标签服务==>发送用户标签删除消息==>失败");
        }
    }

    private void saveToCache(UserTagVO userTagVO) {
        cacheService.set(userProviderCacheKeyBuilder.buildUserTakKey(userTagVO.getUserId()),
                JSON.toJSONString(userTagVO));
    }

    private UserTagVO getFromCache(Long userId) {
        String userTagVo = cacheService.get(userProviderCacheKeyBuilder.buildUserTakKey(userId));
        UserTagVO userTagVO = JSON.parseObject(userTagVo, UserTagVO.class);
        return userTagVO;
    }
}
