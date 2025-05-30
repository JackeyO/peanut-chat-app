package com.sici.chat.canal.sync;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.model.canal.event.DataChangeEvent;

import java.io.Serializable;

/**
 * @author jackey
 * @description
 * @date 5/29/25 17:45
 */
public abstract class AbstractDataChangeHandler<T, ID extends Serializable> implements DataChangeHandler {
    public AbstractDataChangeHandler() {
        DataChangeHandlerFactory.registerDataChangeHandler(this);
    }

    @Override
    public final void handleDataChangeEvent(DataChangeEvent dataChangeEvent) {
        DataChangeEvent.EventType eventType = dataChangeEvent.getEventType();
        switch (eventType) {
            case INSERT:
                handlerInsertDataChangeEvent(dataChangeEvent);
                break;
            case UPDATE:
                handlerUpdateDataChangeEvent(dataChangeEvent);
                break;
            case DELETE:
                handlerDeleteDataChangeEvent(dataChangeEvent);
                break;
            default:
                break;
        }
    }

    public abstract void handlerInsertDataChangeEvent(DataChangeEvent dataChangeEvent);
    public abstract void handlerDeleteDataChangeEvent(DataChangeEvent dataChangeEvent);
    public abstract void handlerUpdateDataChangeEvent(DataChangeEvent dataChangeEvent);

    public abstract IService<T> getIService();
    public abstract ID convertPrimaryKey(Object primaryKey);

    public abstract Boolean checkNecessary(T t, DataChangeEvent dataChangeEvent);
}
