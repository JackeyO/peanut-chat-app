package com.sici.chat.controller.room;


import com.sici.chat.context.RequestInfo;
import com.sici.chat.model.chat.room.vo.GroupRoomJoinedVo;
import com.sici.chat.model.chat.room.vo.GroupRoomSearchVo;
import com.sici.chat.model.chat.room.vo.RoomVO;
import com.sici.chat.util.RequestHolder;
import com.sici.common.enums.code.AppHttpCodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sici.chat.service.room.RoomService;
import com.sici.chat.util.AssertUtil;
import com.sici.common.exception.BusinessException;
import com.sici.common.result.ResponseResult;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;


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
    public ResponseResult<RoomVO> getRoomInfo(@RequestParam Long roomId) {
        // 参数校验
        AssertUtil.notNull(roomId, "房间ID不能为空");
        
        try {
            RoomVO roomVO = roomService.getRoomInfo(roomId);
            return ResponseResult.okResult(roomVO);
        } catch (BusinessException e) {
            log.error("获取房间信息失败", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        }
    }

    /**
     * 查询当前用户加入的群组房间(不包含单聊房间)
     */
    @GetMapping("rooms")
    public ResponseResult<GroupRoomJoinedVo> getUserJoinedGroupRooms() {
        RequestInfo requestInfo = RequestHolder.get();
        AssertUtil.notNull(requestInfo, AppHttpCodeEnum.UNAUTHORIZED);
        AssertUtil.notNull(requestInfo.getUserId(), AppHttpCodeEnum.UNAUTHORIZED);

        try {
            GroupRoomJoinedVo roomVO = roomService.getUserJoinedRooms(requestInfo.getUserId());
            return ResponseResult.okResult(roomVO);
        } catch (BusinessException e) {
            log.error("获取用户房间信息失败", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        }
    }
}