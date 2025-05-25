package com.sici.chat.service.impl.conversation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sici.chat.config.thread.ThreadPoolConfiguration;
import com.sici.chat.dao.ConversationDao;
import com.sici.chat.model.chat.conversation.entity.UserConversation;
import com.sici.chat.model.chat.conversation.vo.ConversationVO;
import com.sici.chat.model.chat.cursor.dto.CursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.room.vo.RoomVO;
import com.sici.chat.service.conversation.ConversationService;
import com.sici.chat.service.room.RoomService;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.chat.util.CursorPageUtil;
import com.sici.framework.redis.RedisUtils;
import jakarta.annotation.Resource;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author jackey
 * @description 会话业务
 * @date 5/24/25 16:15
 */
@Service
public class ConversationServiceImpl implements ConversationService {
    @Resource
    @Qualifier(ThreadPoolConfiguration.CHAT_PUBLIC_EXECUTOR)
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private RoomService roomService;
    @Resource
    private ConversationDao conversationDao;

    @Override
    public CursorPageVo<ConversationVO> getConversationCursorPageList(Long userId, CursorPageDto cursorPageDto) {
        CursorPageVo<UserConversation> cursorPageOfMySql = CursorPageUtil.getCursorPageOfMySql(conversationDao, cursorPageDto, (LambdaQueryWrapper<UserConversation> wrapper, Object cursorValue) -> {
            wrapper.gt(UserConversation::getId, cursorValue);
        }, UserConversation::getId);

        List<ConversationVO> conversationVOList = Optional.ofNullable(cursorPageOfMySql.getRecords())
                .orElse(Lists.newArrayList())
                .stream()
                .map(userConversation -> ConvertBeanUtil.convertSingle(userConversation, ConversationVO.class))
                .collect(Collectors.toList());

        CursorPageVo<ConversationVO> conversationVOCursorPageVo = new CursorPageVo<>();
        conversationVOCursorPageVo.setPageSize(cursorPageOfMySql.getPageSize());
        conversationVOCursorPageVo.setRecordSize(conversationVOList.size());
        conversationVOCursorPageVo.setIsLast(cursorPageOfMySql.getIsLast());
        conversationVOCursorPageVo.setCursor(cursorPageOfMySql.getCursor());

        CompletableFuture.allOf(fillRoomInfo(conversationVOList),
                fillMessageInfo(conversationVOList)
                ).join();

        conversationVOCursorPageVo.setRecords(conversationVOList);
        return conversationVOCursorPageVo;
    }

    private CompletableFuture<?> fillMessageInfo(List<ConversationVO> conversationVOList) {
        return CompletableFuture.runAsync(() -> {
            conversationVOList.forEach(conversationVO -> {
                CompletableFuture.runAsync(() -> {
                    // TODO 获取该会话最后一条消息内容和消息id,以及未读消息数量 - Su Xiao Wen - 5/24/25 16:45
                }, threadPoolExecutor);
            });
        }, threadPoolExecutor);
    }

    private CompletableFuture<Void> fillRoomInfo(List<ConversationVO> conversationVOList) {
        return CompletableFuture.runAsync(() -> {
            conversationVOList.forEach(conversationVO -> {
                CompletableFuture.runAsync(() -> {
                    // 获取会话的房间信息
                    RoomVO roomInfo = roomService.getRoomInfo(conversationVO.getRoomId());
                    conversationVO.setRoomVO(roomInfo);
                }, threadPoolExecutor);
            });
        }, threadPoolExecutor);
    }
}
