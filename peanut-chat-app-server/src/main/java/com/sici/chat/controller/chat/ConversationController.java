package com.sici.chat.controller.chat;

import com.sici.chat.context.RequestInfo;
import com.sici.chat.model.chat.conversation.vo.ConversationVO;
import com.sici.chat.model.chat.cursor.dto.CursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.service.conversation.ConversationService;
import com.sici.chat.util.AssertUtil;
import com.sici.chat.util.RequestHolder;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.exception.BusinessException;
import com.sici.common.result.ResponseResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author jackey
 * @description 会话业务
 * @date 5/22/25 23:58
 */
@RestController
@Slf4j
@RequestMapping("peanut/conversation")
public class ConversationController {
    @Resource
    private ConversationService conversationService;

    /**
     * 获取会话列表
     */
    @PostMapping("list")
    public ResponseResult<CursorPageVo<ConversationVO>> getConversationList(@RequestBody CursorPageDto cursorPageDto) {
        RequestInfo requestInfo = RequestHolder.get();
        AssertUtil.isTrue(!Objects.isNull(requestInfo) && !Objects.isNull(requestInfo.getUserId()),
                AppHttpCodeEnum.UNAUTHORIZED);

        Long userId = requestInfo.getUserId();
        if (Objects.isNull(cursorPageDto)) {
            cursorPageDto = CursorPageDto.defaultOption();
        }

        try {
            CursorPageVo<ConversationVO> conversationList = conversationService.getConversationCursorPageList(userId, cursorPageDto);
            return ResponseResult.okResult(conversationList);
        } catch (BusinessException e) {
            log.error("获取会话列表失败, {}", e);
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error("获取会话列表失败, {}", e);
            return ResponseResult.errorResult(AppHttpCodeEnum.FAIL.getCode(),"获取会话列表失败");
        }
    }
}
