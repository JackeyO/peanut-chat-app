package com.sici.live.interfaces.user.rpc;

import com.sici.common.enums.user.tag.UserTagEnums;
import com.sici.common.result.ResponseResult;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.user.tag.rpc
 * @author: 20148
 * @description: 用户标签RPC接口
 * @create-date: 9/14/2024 8:20 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface IUserTagRpc {
    /**
     * 设置标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    ResponseResult<Boolean> setTag(Long userId, UserTagEnums userTagsEnum);

    /**
     * 取消标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    ResponseResult<Boolean> cancelTag(Long userId, UserTagEnums userTagsEnum);

    /**
     * 是否包含某个标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    ResponseResult<Boolean> containTag(Long userId, UserTagEnums userTagsEnum);
}
