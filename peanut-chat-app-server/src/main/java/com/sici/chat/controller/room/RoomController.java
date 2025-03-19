package com.sici.chat.controller.room;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.service.room.RoomService;
import com.sici.common.result.ResponseResult;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.controller
 * @author: 20148
 * @description: 房间信息相关接口
 * @create-date: 11/24/2024 2:42 PM
 * @version: 1.0
 */

@RestController
@RequestMapping("peanut/room")
@Slf4j
public class RoomController {
    @Resource
    private RoomService roomService;

    /**
     * 根据房间id房间信息
     */
    @GetMapping("info")
    public ResponseResult getRoomInfo(@RequestParam Integer roomId) {
        // TODO: 完成该接口后续（见Service), 并测试该接口 created by 749291 at 2025-03-19 22:33
        return roomService.getRoomInfo(roomId);
    }
}