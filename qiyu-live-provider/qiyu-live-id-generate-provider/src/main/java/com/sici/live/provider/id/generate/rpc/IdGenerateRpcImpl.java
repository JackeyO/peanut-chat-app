package com.sici.live.provider.id.generate.rpc;

import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.common.result.ResponseResult;
import com.sici.live.interfaces.id.generate.rpc.IdGenerateRpc;
import com.sici.live.provider.id.generate.service.IdGenerateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.id.generate.rpc
 * @author: 20148
 * @description: 分布式id生成器
 * @create-date: 9/13/2024 3:24 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
@Slf4j
public class IdGenerateRpcImpl implements IdGenerateRpc {
    @Resource
    private IdGenerateService idGenerateService;

    @Override
    @Operation(summary = "获取有序id")
    @EnableInvokeLog(description = "分布式id生成器RPC服务==>获取有序id")
    public ResponseResult<Long> getSeqId(Integer id) {
        return idGenerateService.getSeqId(id);
    }

    @Override
    @Operation(summary = "分布式id生成器RPC服务==>获取无序id")
    @EnableInvokeLog(description = "获取无序id")
    public ResponseResult<Long> getNoSeqId(Integer id) {
        return idGenerateService.getNoSeqId(id);
    }
}
