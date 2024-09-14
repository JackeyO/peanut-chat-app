package com.sici.live.provider.id.generate.bo;

import com.sici.common.constant.id.generate.IdGenerateServiceConstant;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.LongStream;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.id.generate.bo
 * @author: 20148
 * @description: 本地无序id
 * @create-date: 9/13/2024 5:27 PM
 * @version: 1.0
 */

@Data
public class LocalUnSeqIdBo extends LocalIdBo {
    private static ConcurrentLinkedQueue<Long> noSeqIdQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean currentIdSegIsFull() {
        return noSeqIdQueue.isEmpty();
    }

    @Override
    public boolean currentIdSegOverThreshHold() {
        long length = super.getNextThreshHold() - super.getCurrentStart();
        return noSeqIdQueue.size() <= (1 - IdGenerateServiceConstant.UPDATE_THRESH_RATE) * length;
    }

    @Override
    public void refreshLocalIdSeg() {
        try {
            super.setCurrentStart(super.getCurrentStart() + super.getStep());
            super.setNextThreshHold(super.getNextThreshHold() + super.getStep());
            super.setPushed(false);
            load(getCurrentStart(), getNextThreshHold());
        } catch (Exception e) {
            throw new RuntimeException("id-generate-service==>本地无序id段==>刷新失败");
        }
    }

    public void load(long begin, long end) {
        ArrayList<Long> idSeg = LongStream.iterate(begin, i -> i + 1)
                .limit(end - begin)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        // 打乱顺序后放入本地id段中
        Collections.shuffle(idSeg);
        noSeqIdQueue.addAll(idSeg);
    }

    @Override
    public long getNextId() {
        if (currentIdSegIsFull()) {
            throw new RuntimeException("id-generate-service==>本地无序id段已满，需要等待本地id段刷新");
        }
        return noSeqIdQueue.poll();
    }
}
