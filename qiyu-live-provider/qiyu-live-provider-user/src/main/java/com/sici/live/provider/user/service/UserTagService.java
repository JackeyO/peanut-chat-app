package com.sici.live.provider.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.common.enums.user.tag.UserTagEnums;
import com.sici.common.result.ResponseResult;
import com.sici.live.model.user.tag.pojo.UserTagPO;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.user.service
 * @author: 20148
 * @description: 用户标签服务
 * @create-date: 9/14/2024 8:29 PM
 * @version: 1.0
 */

public interface UserTagService extends IService<UserTagPO> {
    /**
     * 设置标签
     * @param userId
     * @param userTagsEnum
     * @return
     */
    ResponseResult<Boolean> setTag(Long userId, UserTagEnums userTagsEnum);

    /**
     * 取消标签
     * @param userId
     * @param userTagsEnum
     * @return
     */

    ResponseResult<Boolean> cancelTag(Long userId, UserTagEnums userTagsEnum);

    /**
     * 是否包含某个标签
     * @param userId
     * @param userTagsEnum
     * @return
     */
    ResponseResult<Boolean> containTag(Long userId, UserTagEnums userTagsEnum);
}
