package com.sici.live.provider.id.generate.service.impl;

import com.sici.common.result.ResponseResult;
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
    @Test
    public void getSeqId() {
        for (int i = 0; i < 1000; i ++) {
            ResponseResult<Long> seqId = idGenerateService.getSeqId(1);
            System.out.println(seqId.getData());
        }
    }
}