package com.sici.common.enums.im;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.enums.im
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 5:30 PM
 * @version: 1.0
 */

public enum AppIdEnums {
    QIYU_LIVE_APP(1, "sici直播");

    private int appId;
    private String appName;

    AppIdEnums(int appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }


    public int getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }
}
