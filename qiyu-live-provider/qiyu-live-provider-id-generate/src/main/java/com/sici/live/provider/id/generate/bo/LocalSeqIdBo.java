package com.sici.live.provider.id.generate.bo;

import com.sici.common.constant.id.generate.IdGenerateServiceConstant;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.id.generate.bo
 * @author: 20148
 * @description: 本地id段
 * @create-date: 9/13/2024 3:33 PM
 * @version: 1.0
 */

@Data
public class LocalSeqIdBo extends LocalIdBo {
    private AtomicLong currentNum;

    @Override
    public boolean currentIdSegIsFull() {
        return currentNum.get() >= super.getNextThreshHold();
    }

    @Override
    public boolean currentIdSegOverThreshHold() {
        long length = super.getNextThreshHold() - super.getCurrentStart();
        return currentNum.get() - super.getCurrentStart() >= IdGenerateServiceConstant.UPDATE_THRESH_RATE * length;
    }
    @Override
    public void refreshLocalIdSeg() {
        try {
            super.setCurrentStart(getCurrentStart() + getStep());
            super.setNextThreshHold(getNextThreshHold() + getStep());
            setCurrentNum(new AtomicLong(getCurrentStart()));
            super.setPushed(false);
        } catch (Exception e) {
            throw new RuntimeException("id-generate-service==>本地有序id段==>刷新失败");
        }
    }

    @Override
    public long getNextId() {
        if (currentIdSegIsFull()) {
            throw new RuntimeException("id-generate-service==>本地有序id段已满，需要等待本地id段刷新");
        }
        return currentNum.getAndIncrement();
    }
}
