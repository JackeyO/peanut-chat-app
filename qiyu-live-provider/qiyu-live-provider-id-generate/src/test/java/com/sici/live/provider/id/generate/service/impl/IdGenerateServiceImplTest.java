package com.sici.live.provider.id.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sici.common.result.ResponseResult;
import com.sici.live.model.id.generate.pojo.IdGeneratePO;
import com.sici.live.provider.id.generate.mapper.IdGenerateMapper;
import com.sici.live.provider.id.generate.service.IdGenerateService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IdGenerateServiceImplTest {

    @Resource
    private IdGenerateService idGenerateService;
    @Resource
    private IdGenerateMapper idGenerateMapper;
    @Test
    public void getSeqId() {
        for (int i = 0; i < 1000; i ++) {
            ResponseResult<Long> seqId = idGenerateService.getSeqId(1);
            System.out.println(seqId.getData());
        }
    }

    @Test
    public void getUnSeqId() {
        for (int i = 0; i < 1000; i ++) {
            ResponseResult<Long> noSeqId = idGenerateService.getNoSeqId(2);
            System.out.println(noSeqId.getData());
        }
    }
}