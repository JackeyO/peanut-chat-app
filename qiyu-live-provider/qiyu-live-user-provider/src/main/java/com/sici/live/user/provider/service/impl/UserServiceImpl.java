package com.sici.live.user.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.bean.ConvertBeanUtil;
import com.sici.common.constant.user.UserProviderConstant;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.result.ResponseResult;
import com.sici.framework.redis.CacheService;
import com.sici.framework.redis.key.RedisKeyEntry;
import com.sici.live.model.user.dto.UserDTO;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.model.user.vo.UserVO;
import com.sici.live.user.provider.mapper.UserMapper;
import com.sici.live.user.provider.redis.key.UserProviderCacheKeyBuilder;
import com.sici.live.user.provider.service.UserService;
import com.sici.redis.RedisExpireTimeUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/10/2024 6:43 PM
 * @version: 1.0
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    @Resource
    private CacheService cacheService;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult<UserVO> getUser(Long userId) {
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        String user = cacheService.get(userProviderCacheKeyBuilder.buildUserInfoKey(userId));
        UserVO userVo = null;
        if (!StringUtils.isEmpty(user)) {
            userVo = JSON.parseObject(user, UserVO.class);
        }
        if (userVo == null) {
            UserPO userPo = getById(userId);
            if (userPo != null) {
                userVo = ConvertBeanUtil.convertSingle(userPo, UserVO.class);
                // 存入缓存
                cacheService.setEx(userProviderCacheKeyBuilder.buildUserInfoKey(userId),
                        JSON.toJSONString(userVo), UserProviderConstant.USER_INFO_CACHE_EXPIRE, TimeUnit.MINUTES);
            }
        }
        return ResponseResult.okResult(userVo);
    }

    @Override
    public ResponseResult<List<UserVO>> getUsers(List<Long> userBatchIds) {
        List<UserVO> userVos = new ArrayList<>();
        if (CollectionUtils.isEmpty(userBatchIds)) {
            return ResponseResult.okResult(userVos);
        }

        // 获取key
        List<String> keys = userProviderCacheKeyBuilder.buildUserInfoKey(userBatchIds);

        // 从redis中查询数据
        Map<Long, Boolean> inRedis = new HashMap<>();
        List<String> users = cacheService.multiGet(keys);
        users.stream()
                .filter(user -> !StringUtils.isEmpty(user))
                .map(user -> JSON.parseObject(user, UserPO.class))
                .forEach(userPO -> {
                    userVos.add(ConvertBeanUtil.convertSingle(userPO, UserVO.class));
                    inRedis.put(userPO.getUserId(), true);
                });

        // 将没有从redis中查到的userId先分组再从数据库中查询
        Map<Long, List<Long>> notInRedisGroupedIds = userBatchIds.stream()
                .parallel()
                .filter(userId -> !inRedis.containsKey(userId))
                .collect(Collectors.groupingBy(userId -> userId % UserProviderConstant.USER_TABLE_KEY_MOD));

        notInRedisGroupedIds.values()
                .stream()
                .forEach(userIds -> {
                    List<UserVO> userVosTmp = userMapper.selectBatchIds(userIds)
                                    .stream().filter(userPO -> userPO != null).collect(Collectors.toList())
                                    .stream().map(userPO -> ConvertBeanUtil.convertSingle(userPO, UserVO.class))
                                    .collect(Collectors.toList());
                    userVos.addAll(userVosTmp);

                    Map<RedisKeyEntry, String> userVosMap = userVosTmp.stream()
                            .collect(Collectors.toMap(userVo ->
                                            RedisKeyEntry.builder()
                                                    .value(userProviderCacheKeyBuilder.buildUserInfoKey(userVo.getUserId()))
                                                    .expire(RedisExpireTimeUtil.createRandomExpireTime())
                                                    .timeUnit(TimeUnit.MINUTES).build()
                                    ,
                                    userVo -> JSON.toJSONString(userVo)));
                    // 保存到redis
                    cacheService.multiSetByPipeLine(userVosMap);
                });

        return ResponseResult.okResult(userVos);
    }

    @Override
    public ResponseResult removeUser(UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        UserPO userPO = ConvertBeanUtil.convertSingle(userDTO, UserPO.class);

        removeById(userPO);

        // 延迟双删
        cacheService.delete(userProviderCacheKeyBuilder.buildUserInfoKey(userPO.getUserId()));
        return null;
    }

    @Override
    public ResponseResult saveUser(UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        UserPO userPO = ConvertBeanUtil.convertSingle(userDTO, UserPO.class);
        boolean save = save(userPO);
        return save ? ResponseResult.okResult() : ResponseResult.errorResult();
    }

    @Override
    public ResponseResult updateUser(UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        UserPO userPO = ConvertBeanUtil.convertSingle(userDTO, UserPO.class);

        boolean updated = updateById(userPO);
        return updated ? ResponseResult.okResult() : ResponseResult.errorResult();
    }
}

