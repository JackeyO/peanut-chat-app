package com.sici.common.exception;

import com.sici.common.enums.code.AppHttpCodeEnum;

public class BusinessException extends RuntimeException {
    private Integer errorCode;
    private String errorMsg;

    public BusinessException() {
        super();
    }

    public BusinessException(Integer errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMsg = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
