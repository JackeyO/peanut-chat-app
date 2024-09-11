package com.sici.live.user.provider.rpc;

import com.alibaba.fastjson.JSON;
import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.user.interfaces.rpc.IUserRpc;
import com.sici.live.user.provider.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.runtime.Resources;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.List;

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
@Component
public class UserRpcImpl implements IUserRpc {
    @Resource
    private UserService userService;
    @Override
    @EnableInvokeLog(description = "test dubbo")
    public String test() {
        List<UserPO> list = userService.list();
        return JSON.toJSONString(list);
    }
}