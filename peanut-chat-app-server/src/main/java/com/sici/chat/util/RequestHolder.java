package com.sici.chat.util;

import com.sici.chat.context.RequestInfo;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.util
 * @author: 20148
 * @description: 请求上下文信息
 * @create-date: 12/27/2024 5:53 PM
 * @version: 1.0
 */

public class RequestHolder {
    public static ThreadLocal<RequestInfo> requestInfoThreadLocal = new ThreadLocal<RequestInfo>();

    public static RequestInfo get() {
        return requestInfoThreadLocal.get();
    }

    public static void set(RequestInfo requestInfo) {
        requestInfoThreadLocal.set(requestInfo);
    }

    public static void remove() {
        requestInfoThreadLocal.remove();
    }
}
