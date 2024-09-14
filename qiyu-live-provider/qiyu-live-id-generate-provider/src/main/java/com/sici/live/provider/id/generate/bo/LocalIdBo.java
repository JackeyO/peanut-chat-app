package com.sici.live.provider.id.generate.bo;

import com.sici.live.model.id.generate.pojo.IdGeneratePO;
import lombok.Data;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.id.generate.bo
 * @author: 20148
 * @description:
 * @create-date: 9/13/2024 6:02 PM
 * @version: 1.0
 */

@Data
public abstract class LocalIdBo {
    private int id;
    private long currentStart;
    private long nextThreshHold;
    private int step;
    private volatile boolean pushed;
    public boolean hasPushed() {
        return pushed;
    }
    abstract public boolean currentIdSegIsFull();
    abstract public boolean currentIdSegOverThreshHold();
    abstract public void refreshLocalIdSeg();

    abstract public long getNextId();
}