package com.sici.chat.canal.sync;

import java.io.Serializable;

/**
 * @author jackey
 * @description
 * @date 5/29/25 17:45
 */
public abstract class AbstractDataChangeRedisHandler<T, ID extends Serializable> extends AbstractDataChangeHandler<T, ID> {
    public AbstractDataChangeRedisHandler() {
        super();
    }

    @Override
    public String getSyncType() {
        return "REDIS";
    }
}
