package com.sici.live.api.controller;

import com.sici.live.user.interfaces.rpc.IUserRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.api.controller
 * @author: 20148
 * @description: 测试duboo rpc调用
 * @create-date: 9/10/2024 3:23 PM
 * @version: 1.0
 */

@RestController
public class TestDubboController {
    @DubboReference
    private IUserRpc iUserRpc;
    @GetMapping("testDubbo")
    public String testDubbo() {
        iUserRpc.test();
        return "ok";
    }
}
