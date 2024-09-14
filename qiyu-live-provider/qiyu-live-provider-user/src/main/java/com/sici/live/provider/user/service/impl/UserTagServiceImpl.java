package com.sici.live.provider.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.common.ReflectUtils;
import com.sici.common.constant.user.tag.UserTagConstant;
import com.sici.common.enums.user.tag.UserTagEnums;
import com.sici.common.result.ResponseResult;
import com.sici.live.model.user.tag.pojo.UserTagPO;
import com.sici.live.provider.user.mapper.UserTagMapper;
import com.sici.live.provider.user.service.UserTagService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.runtime.Resources;
import org.springframework.stereotype.Service;

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
    @Override
    public ResponseResult<Boolean> setTag(Long userId, UserTagEnums userTagsEnum) {
        int effected = userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag());
        return effected > 0 ? ResponseResult.okResult(true) : ResponseResult.errorResult();
    }

    @Override
    public ResponseResult<Boolean> cancelTag(Long userId, UserTagEnums userTagsEnum) {
        int effected = userTagMapper.cancelTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag());
        return effected > 0 ? ResponseResult.okResult(true) : ResponseResult.errorResult();
    }

    @Override
    public ResponseResult<Boolean> containTag(Long userId, UserTagEnums userTagsEnum) {
        UserTagPO userTagPo = getById(userId);

        Long tagInfo = (Long) ReflectUtils.getPropertie(userTagPo, UserTagConstant.TAG_INFO_ATTRIBUTE_NAME_MAP.get(
                userTagsEnum.getFieldName()
        ));

        Boolean contains = (tagInfo & userTagsEnum.getTag()) != 0;

        return contains ? ResponseResult.okResult(contains) : ResponseResult.errorResult();
    }
}
