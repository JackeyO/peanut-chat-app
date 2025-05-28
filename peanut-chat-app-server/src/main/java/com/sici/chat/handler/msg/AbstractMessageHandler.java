package com.sici.chat.handler.msg;

import cn.hutool.core.bean.BeanUtil;
import com.sici.chat.cache.room.RoomMessageCache;
import com.sici.chat.config.thread.ThreadPoolConfiguration;
import com.sici.chat.dao.MessageDao;
import com.sici.chat.model.chat.message.dto.MessageDto;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.handler
 * @author: 20148
 * @description: 消息处理器抽象
 * @create-date: 11/25/2024 5:30 PM
 * @version: 1.0
 */

public abstract class AbstractMessageHandler<Req extends MessageDto> {
    @Resource
    private MessageDao messageDao;
    @Resource
    private RoomMessageCache roomMessageCache;
    @Resource
    @Qualifier(ThreadPoolConfiguration.CHAT_PUBLIC_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private Class<Req> messageBodyClass;

    /**
     * 子类初始化时自动向工厂注册
     */
    @PostConstruct
    public void init() {
        ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.messageBodyClass = (Class<Req>) superclass.getActualTypeArguments()[0];
        MessageHandlerFactory.register(this);
    }

    public boolean support(Req body) {
        return messageBodyClass.isInstance(body);
    }

    public abstract MessageRespTypeEnum getMessageTypeEnum();

    /**
     * 解构出消息体
     *
     * @param messageReq
     * @return
     */
    public abstract Req getBody(MessageRequestDto messageReq);

    /**
     * 留给子类去扩展消息额外检查
     *
     * @return
     */
    public abstract void extCheck(Req body);

    /**
     * 子类扩展消息持久化
     */
    public abstract ChatMessageVo extSave(Message message, Req body);

    public void check(Req body) {
    }

    public Message saveToDb(Req body) {
        Message message = new Message();
        BeanUtil.copyProperties(body, message);
        messageDao.save(message);
        return message;
    }
    public void saveToCache(ChatMessageVo chatMessageVo) {
        roomMessageCache.add(chatMessageVo.getMessage().getRoomId(), chatMessageVo);
    }

    public Message checkAndSave(MessageRequestDto messageReq) {
        Req body = getBody(messageReq);
        // 消息验证
        check(body);
        extCheck(body);

        // 保存到db
        Message message = saveToDb(body);

        CompletableFuture.runAsync(() -> {
            // 扩展消息持久化
            ChatMessageVo chatMessageVo = extSave(message, body);

            // 保存到缓存
            saveToCache(chatMessageVo);
        }, threadPoolTaskExecutor);
        return message;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public Class<Req> getMessageBodyClass() {
        return messageBodyClass;
    }
}