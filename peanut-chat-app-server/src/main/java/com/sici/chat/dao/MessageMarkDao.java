package com.sici.chat.dao;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.MessageMarkMapper;
import com.sici.chat.model.chat.message.entity.MessageMark;
import com.sici.common.enums.chat.message.MessageMarkActionEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 20148
 * @description 针对表【message_mark】的数据库操作Service实现
 * @createDate 2024-12-03 13:48:19
 */
@Component
public class MessageMarkDao extends ServiceImpl<MessageMarkMapper, MessageMark> {
    public Pair<Long, Long> getLikeAndDislikeCount(Long id) {
        return Pair.of(getLikeCount(id), getDislikeCount(id));
    }

    public List<Long> getLikeUser(Long id) {
        return lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getLikeFlag, MessageMarkActionEnum.YES.getCode())
                .list().stream()
                .map(MessageMark::getUserId)
                .collect(Collectors.toList());
    }

    public List<Long> getDisLikeUser(Long id) {
        return lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getDislikeFlag, MessageMarkActionEnum.YES.getCode())
                .list().stream()
                .map(MessageMark::getUserId)
                .collect(Collectors.toList());
    }

    public Long getLikeCount(Long id) {
        Long likes = lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getLikeFlag, MessageMarkActionEnum.YES.getCode())
                .count();
        return likes;
    }

    public Long getDislikeCount(Long id) {
        Long dislikes = lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getDislikeFlag, MessageMarkActionEnum.YES.getCode())
                .count();
        return dislikes;
    }
}




