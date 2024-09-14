package com.sici.live.provider.id.generate.service;

import com.sici.common.result.ResponseResult;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.id.generate.service
 * @author: 20148
 * @description: 分布式id生成
 * @create-date: 9/13/2024 3:25 PM
 * @version: 1.0
 */

public interface IdGenerateService {

    /**
     * 获取有序id
     * @param id
     * @return
     */
    ResponseResult<Long> getSeqId(Integer id);

    /**
     * 获取无序id
     * @param id
     * @return
     */
    ResponseResult<Long> getNoSeqId(Integer id);
}
