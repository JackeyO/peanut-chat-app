package com.sici.live.user.provider.rpc;

import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.live.user.interfaces.rpc.IUserRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/10/2024 12:18 PM
 * @version: 1.0
 */

@DubboService
@Slf4j
public class UserRpcImpl implements IUserRpc {
    @Override
    @EnableInvokeLog(description = "test dubbo")
    public String test() {
        return null;
    }
}