package com.sici.chat.util;

import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.model.chat.cursor.dto.CursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.util
 * @author: 20148
 * @description: 游标分页工具类
 * @create-date: 12/10/2024 5:26 PM
 * @version: 1.0
 */

public class CursorPageUtil {
    public static <T> CursorPageVo<T> getCursorPageOfMySql(IService<T> dao,
                                                           CursorPageDto cursorPageDto,
                                                           BiConsumer<LambdaQueryWrapper<T>, Object> initWrapper,
                                                           SFunction<T, ?> cursorMap) {
        // 获取游标的类型
        Class<?> cursorType = LambdaUtils.getReturnType(cursorMap);
        Object cursorValue = parseCursor(cursorPageDto.getCursor(), cursorType);
        // 初始化wrapper(加上自定义过滤条件和游标条件)
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        initWrapper.accept(wrapper, cursorValue);

        // 封装返回数据
        CursorPageVo<T> cursorPageVo = new CursorPageVo<>();
        Page<T> page = dao.page(new Page<T>(1, cursorPageDto.getPageSize()), wrapper);
        List<T> records = page.getRecords();
        cursorPageVo.setPageSize(cursorPageDto.getPageSize());
        cursorPageVo.setRecords(records);
        cursorPageVo.setRecordSize(records.size());
        cursorPageVo.setIsLast(records.size() == page.getTotal());

        // 设置下一次游标
        String nextCursor = records.isEmpty() ? null : cursorToString(cursorMap.apply(records.get(records.size() - 1)));
        cursorPageVo.setCursor(nextCursor);
        return cursorPageVo;
    }

    /**
     * String类型游标转为对应的值类型
     * @param cursor
     * @param cursorType
     * @return
     * @param <T>
     */
    public static <T> T parseCursor(String cursor, Class<T> cursorType) {
        if (cursor == null) return null;
        if (Date.class.isAssignableFrom(cursorType)) {
            return (T) new Date(Long.parseLong(cursor));
        }
        return JSON.parseObject(cursor, cursorType);
    }

    /**
     * 游标值类型转为String
     * @param cursor
     * @return
     */
    public static String cursorToString(Object cursor) {
        if (cursor == null) {
            return null;
        }
        if (cursor instanceof Date) {
            return String.valueOf(((Date) cursor).getTime());
        }
        return JSON.toJSONString(cursor);
    }

    public static void main(String[] args) {
    }
}