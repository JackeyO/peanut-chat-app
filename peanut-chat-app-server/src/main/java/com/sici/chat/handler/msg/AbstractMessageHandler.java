package com.sici.chat.handler.msg;

import cn.hutool.core.bean.BeanUtil;
import com.sici.chat.dao.MessageDao;
import com.sici.chat.model.chat.message.dto.MessageDto;
import com.sici.chat.model.chat.message.dto.MessageRequestDto;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.common.enums.chat.message.MessageTypeEnum;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;

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
    private Class<Req> messageBodyClass;

    /**
     * 子类初始化时自动向工厂注册
     */
    @PostConstruct
    public void init() {
        ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.messageBodyClass = (Class<Req>) superclass.getActualTypeArguments()[1];
        MessageHandlerFactory.register(this);
    }

    public boolean support(MessageRequestDto messageRequestDto) {
        return messageRequestDto.getBody() != null && messageBodyClass.isInstance(messageRequestDto.getBody());
    }

    public abstract MessageTypeEnum getMessageTypeEnum();

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
    public abstract void extCheck(MessageRequestDto messageReq);

    /**
     * 子类扩展消息持久化
     */
    public abstract void extSave(Message message, MessageRequestDto messageReq);

    public void check(MessageRequestDto messageReq) {
    }

    public Message save(MessageRequestDto messageReq) {
        Req body = getBody(messageReq);
        Message message = new Message();
        BeanUtil.copyProperties(body, message);
        messageDao.save(message);
        return message;
    }

    public Message checkAndSave(MessageRequestDto messageReq) {
        check(messageReq);
        extCheck(messageReq);
        Message message = save(messageReq);
        extSave(message, messageReq);
        return message;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public Class<Req> getMessageBodyClass() {
        return messageBodyClass;
    }
}