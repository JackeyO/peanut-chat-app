package com.sici.chat.cache;

import com.sici.chat.model.chat.message.vo.RoomMemberListBo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TwoPersonRoomCacheTest {
    @Resource
    private TwoPersonRoomCache twoPersonRoomCache;

    @Test
    void testTwo() {
        RoomMemberListBo one = twoPersonRoomCache.getOne(11);
    }
}