package com.sici.chat.canal.sync.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.cache.user.UserBaseInfoCache;
import com.sici.chat.canal.sync.AbstractDataChangeRedisHandler;
import com.sici.chat.dao.UserDao;
import com.sici.chat.model.canal.event.DataChangeEvent;
import com.sici.chat.model.user.entity.User;
import com.sici.common.constant.canal.DatabaseConstant;
import jakarta.annotation.Resource;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jackey
 * @description
 * @date 5/29/25 18:13
 */
public class UserDataChangeRedisHandler extends AbstractDataChangeRedisHandler<User, Long> {
    @Resource
    private UserDao userDao;
    @Resource
    private UserBaseInfoCache userBaseInfoCache;

    @Override
    public IService<User> getIService() {
        return userDao;
    }

    @Override
    public Long convertPrimaryKey(Object primaryKey) {
        return Long.valueOf(String.valueOf(primaryKey));
    }

    @Override
    public String getDatabase() {
        return DatabaseConstant.CHAT_DB_NAME;
    }

    @Override
    public String getTable() {
        return DatabaseConstant.CHAT_USER_TABLE;
    }

    @Override
    public void handlerInsertDataChangeEvent(DataChangeEvent dataChangeEvent) {
        Long userId = convertPrimaryKey(dataChangeEvent.getPrimaryKey());
        User user = userDao.getById(userId);
        userBaseInfoCache.set(userId, user);
    }

    @Override
    public void handlerDeleteDataChangeEvent(DataChangeEvent dataChangeEvent) {
        Long userId = convertPrimaryKey(dataChangeEvent.getPrimaryKey());
        userBaseInfoCache.deleteFromCache(userId);
    }

    @Override
    public void handlerUpdateDataChangeEvent(DataChangeEvent dataChangeEvent) {
        Long userId = convertPrimaryKey(dataChangeEvent.getPrimaryKey());
        User user = userDao.getById(userId);
        userBaseInfoCache.set(userId, user);
    }

    private User convertMapToUser(Map<String, Object> userMap) {
        if (Objects.isNull(userMap)) {
            return null;
        }
        Map<String, Object> userCameralColumnMap = userMap.entrySet()
                .stream()
                .map(e -> Map.entry(StrUtil.toCamelCase(e.getKey()), e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return BeanUtil.toBean(userCameralColumnMap, User.class);
    }

    @Override
    public Boolean checkNecessary(User user, DataChangeEvent dataChangeEvent) {
        // 所有用户变更都需要同步到Redis
        return Boolean.TRUE;
    }
}
