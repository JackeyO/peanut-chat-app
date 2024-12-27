package com.sici.common.result;

import com.sici.common.enums.code.AppHttpCodeEnum;

import java.io.Serializable;

/**
 * 通用的结果返回类
 * @param <T>
 */
public class ResponseResult<T> implements Serializable {

    private String host;

    private Integer code;

    private String errorMessage;

    private T data;

    public ResponseResult() {
        this.code = 200;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.errorMessage = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
    }

    public static ResponseResult errorResult() {
        ResponseResult responseResult = generateResponseResult();
        setAppHttpCodeEnum(responseResult, AppHttpCodeEnum.FAIL);
        return responseResult;
    }
    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult responseResult = generateResponseResult();
        errorSet(responseResult, code, msg);
        return responseResult;
    }

    public static ResponseResult okResult() {
        ResponseResult responseResult = generateResponseResult();
        setAppHttpCodeEnum(responseResult, AppHttpCodeEnum.SUCCESS);
        return responseResult;
    }
    public static ResponseResult okResult(String msg) {
        ResponseResult responseResult = generateResponseResult();
        setAppHttpCodeEnum(responseResult, AppHttpCodeEnum.SUCCESS);
        responseResult.setErrorMessage(msg);
        return responseResult;
    }
    public static ResponseResult okResult(int code, String msg) {
        ResponseResult responseResult = generateResponseResult();
        okSet(responseResult, code, null, msg);
        return responseResult;
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult responseResult = generateResponseResult();
        setAppHttpCodeEnum(responseResult, AppHttpCodeEnum.SUCCESS);
        setData(responseResult, data);
        return responseResult;
    }

    public static ResponseResult generateResponseResult() {
        return new ResponseResult();
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums){
        ResponseResult responseResult = generateResponseResult();
        setAppHttpCodeEnum(responseResult, enums.getCode(), enums.getErrorMessage());
        return responseResult;
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums, String errorMessage){
        ResponseResult responseResult = generateResponseResult();
        setAppHttpCodeEnum(responseResult, enums.getCode(), errorMessage);
        return responseResult;
    }

    public static void setAppHttpCodeEnum(ResponseResult responseResult, AppHttpCodeEnum enums){
        setAppHttpCodeEnum(responseResult, enums.getCode(), enums.getErrorMessage());
    }

    private static void setAppHttpCodeEnum(ResponseResult responseResult, Integer code, String errorMessage){
        responseResult.setCode(code);
        responseResult.setErrorMessage(errorMessage);
    }

    public static void setData(ResponseResult responseResult, Object data) {
        responseResult.setData(data);
    }

    public static void errorSet(ResponseResult responseResult) {
        setAppHttpCodeEnum(responseResult, AppHttpCodeEnum.FAIL);
    }
    public static void errorSet(ResponseResult responseResult, Integer code, String msg) {
        setAppHttpCodeEnum(responseResult, code, msg);
    }

    public static void okSet(ResponseResult responseResult) {
        setAppHttpCodeEnum(responseResult, AppHttpCodeEnum.SUCCESS);
    }
    public static void okSet(ResponseResult responseResult, Object data) {
        setAppHttpCodeEnum(responseResult, AppHttpCodeEnum.SUCCESS);
        responseResult.setData(data);
    }
    public static void okSet(ResponseResult responseResult, Integer code, String msg) {
        setAppHttpCodeEnum(responseResult, code, msg);
    }
    public static void okSet(ResponseResult responseResult, Integer code, Object data, String msg) {
        okSet(responseResult, code, msg);
        responseResult.setData(data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public static void main(String[] args) {
        //前置
        /*AppHttpCodeEnum success = AppHttpCodeEnum.SUCCESS;
        System.out.println(success.getTag());
        System.out.println(success.getErrorMessage());*/

        //查询一个对象
        /*Map map = new HashMap();
        map.put("name","zhangsan");
        map.put("age",18);
        ResponseResult result = ResponseResult.okResult(map);
        System.out.println(JSON.toJSONString(result));*/


        //新增，修改，删除  在项目中统一返回成功即可
       /* ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS);
        System.out.println(JSON.toJSONString(result));*/


        //根据不用的业务返回不同的提示信息  比如：当前操作需要登录、参数错误
        /*ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        System.out.println(JSON.toJSONString(result));*/

        //查询分页信息
//        PageResponseResult responseResult = new PageResponseResult(1,5,50);
//        List list = new ArrayList();
//        list.add("itcast");
//        list.add("itheima");
//        responseResult.setData(list);
//        System.out.println(JSON.toJSONString(responseResult));

    }

}
