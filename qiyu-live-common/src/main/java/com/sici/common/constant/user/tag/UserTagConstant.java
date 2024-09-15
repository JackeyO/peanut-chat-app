package com.sici.common.constant.user.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.constant.user.tag
 * @author: 20148
 * @description: 用户标签相关常量
 * @create-date: 9/14/2024 8:58 PM
 * @version: 1.0
 */

public class UserTagConstant {
    // 数据库标签字段名与java实体类字段名映射关系
    public static final Map<String, String> TAG_INFO_ATTRIBUTE_NAME_MAP = new HashMap<>();

    public static final Integer USER_TAG_LOCK_KEY_EXPIRE = 60;
    static {
        TAG_INFO_ATTRIBUTE_NAME_MAP.put("tag_info_01", "tagInfo01");
        TAG_INFO_ATTRIBUTE_NAME_MAP.put("tag_info_02", "tagInfo02");
        TAG_INFO_ATTRIBUTE_NAME_MAP.put("tag_info_03", "tagInfo03");
    }
}
