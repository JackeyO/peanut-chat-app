package com.sici.live.interfaces.id.generate.rpc;

import com.sici.common.result.ResponseResult;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.id.generate.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/13/2024 3:23 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface IdGenerateRpc {

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
