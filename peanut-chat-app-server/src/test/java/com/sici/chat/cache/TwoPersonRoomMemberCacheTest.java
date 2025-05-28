package com.sici.chat.cache;

import com.sici.chat.cache.room.TwoPersonRoomMemberCache;
import com.sici.chat.model.chat.room.cache.RoomMemberCacheInfo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TwoPersonRoomMemberCacheTest {
    @Resource
    private TwoPersonRoomMemberCache twoPersonRoomMemberCache;

    @Test
    void testTwo() {
        RoomMemberCacheInfo one = twoPersonRoomMemberCache.getOne(11L);
    }
}