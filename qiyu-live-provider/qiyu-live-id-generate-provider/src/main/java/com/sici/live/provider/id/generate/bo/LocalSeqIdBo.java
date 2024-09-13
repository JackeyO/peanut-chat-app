package com.sici.live.provider.id.generate.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSeqIdBo {
    private int id;
    private AtomicLong currentNum;
    private long currentStart;
    private long nextThreshHold;
}
