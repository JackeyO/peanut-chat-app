package com.sici.common.enums.id.generate;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.enums.id.generate
 * @author: 20148
 * @description:
 * @create-date: 9/14/2024 2:39 PM
 * @version: 1.0
 */


public enum IdGenerateEnums {
     SEQ_ID(1, "有序id");

     private int code;
     private String des;

    IdGenerateEnums(int code, String des) {
        setCode(code);
        setDes(des);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
