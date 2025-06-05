package com.sici.framework.agent.message;

import com.alibaba.fastjson2.JSON;

public abstract class BaseMessage {
    public abstract String getType();

    public abstract String toText();

    public String dumpJson() {
        return JSON.toJSONString(this);
    }
}