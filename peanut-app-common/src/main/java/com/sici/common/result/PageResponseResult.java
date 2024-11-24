package com.sici.common.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class PageResponseResult extends ResponseResult implements Serializable {
    private Long currentPage;
    private Long size;
    private Long total;

    public PageResponseResult(Long currentPage, Long size, Long total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
    }

    public static PageResponseResult generatePageResponseResult() {
        return new PageResponseResult();
    }

    public static PageResponseResult generatePageResponseResult(Long currentPage, Long size, Long total) {
        return new PageResponseResult(currentPage, size, total);
    }

    public static PageResponseResult okResult(Long currentPage, Long size, Long total, Object data) {
        PageResponseResult pageResponseResult = generatePageResponseResult(currentPage, size, total);
        okSet(pageResponseResult, data);
        return pageResponseResult;
    }

    public static ResponseResult errorResult(){
        ResponseResult responseResult = generateResponseResult();
        errorSet(responseResult);
        return responseResult;
    }


    public PageResponseResult() {
    }
}
