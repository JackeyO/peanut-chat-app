package com.sici.live.user.interfaces.rpc;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.rpc.api
 * @author: 20148
 * @description: 用户rpc服务
 * @create-date: 9/9/2024 9:17 PM
 * @version: 1.0
 */

@DubboService
public interface IUserRpc {
    String test();
}
