package com.sici.chat.service.impl.conversation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sici.chat.cache.ConversationActivityCache;
import com.sici.chat.cache.RoomMessageCache;
import com.sici.chat.config.thread.ThreadPoolConfiguration;
import com.sici.chat.dao.ConversationDao;
import com.sici.chat.model.chat.conversation.cache.ConversationActivityCacheInfo;
import com.sici.chat.model.chat.conversation.entity.UserConversation;
import com.sici.chat.model.chat.conversation.vo.ConversationVO;
import com.sici.chat.model.chat.cursor.dto.CursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.room.vo.RoomVO;
import com.sici.chat.service.conversation.ConversationService;
import com.sici.chat.service.room.RoomService;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.chat.util.CursorPageUtil;
import jakarta.annotation.Resource;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    @Resource
    private ConversationActivityCache conversationActivityCache;
    @Resource
    private RoomMessageCache roomMessageCache;

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
                    Long roomId = conversationVO.getRoomId();
                    Long conversationId = conversationVO.getId();

                    // 获取会话最后活跃时间
                    ConversationActivityCacheInfo conversationActivityInfo = conversationActivityCache.getOne(conversationId);
                    // 获取房间最后一条消息
                    ChatMessageVo chatMessageVo = Objects.requireNonNull(roomMessageCache.reverseRange(roomId, 0, 0))
                            .stream()
                            .findFirst()
                            .orElse(null);
                    if (Objects.nonNull(chatMessageVo)) {
                        conversationVO.setLastMsg(chatMessageVo.getMessage().getContent());
                        if (Objects.nonNull(conversationActivityInfo) && Objects.nonNull(conversationActivityInfo.getLastActivityTime())) {
                            // 获取未读数量
                            Long unReadCount = roomMessageCache.count(roomId, conversationActivityInfo.getLastActivityTime() + 1, chatMessageVo.getMessage().getSendTime().getTime() + 1);
                            conversationVO.setUnreadCount(unReadCount);
                        }
                    }
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
