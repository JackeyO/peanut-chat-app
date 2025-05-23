package com.sici.chat.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.sici.chat.model.user.entity.User;
import com.sici.common.constant.jwt.JwtConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.util
 * @author: 20148
 * @description: jwt工具类
 * @create-date: 12/18/2024 4:36 PM
 * @version: 1.0
 */

public class JwtUtil {
    private static Map<String, Object> getDefaultPayload() {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.DAY_OF_MONTH, 10);

        Map<String,Object> payload = new HashMap<>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        return payload;
    }
    public static String createUserToken(User user) {
        Map<String, Object> payload = getDefaultPayload();
        payload.put(JwtConstant.USER_ID_PAYLOAD_KEY, user.getId());
        String key = JwtConstant.USER_ID_KEY;
        String token = JWTUtil.createToken(payload, key.getBytes());
        return token;
    }
    private static boolean verifyDefaultPayload(String token, String key) {
        JWT jwt = JWTUtil.parseToken(token);
        if (!jwt.setKey(key.getBytes()).validate(0)) return false;
        return true;
    }

    public static Long getUidFromToken(String token) {
        if (!verifyDefaultPayload(token, JwtConstant.USER_ID_KEY)) return null;
        JWT jwt = JWTUtil.parseToken(token);
        return (Long) jwt.getPayload(JwtConstant.USER_ID_PAYLOAD_KEY);
    }
}
