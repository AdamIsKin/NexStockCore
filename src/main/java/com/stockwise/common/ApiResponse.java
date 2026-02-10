package com.stockwise.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 统一响应体，与《StockWise 前后端开发范式》约定一致，JSON 字段名为 ErrNo/ErrMsg/Data/TraceID。
 */
public class ApiResponse<T> {

    @JsonProperty("ErrNo")
    private int errNo;

    @JsonProperty("ErrMsg")
    private String errMsg;

    @JsonProperty("Data")
    private T data;

    @JsonProperty("TraceID")
    private String traceID;

    public static <T> ApiResponse<T> of(int errNo, String errMsg, T data, String traceID) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setErrNo(errNo);
        r.setErrMsg(errMsg != null ? errMsg : "");
        r.setData(data);
        r.setTraceID(traceID != null ? traceID : "");
        return r;
    }

    public int getErrNo() { return errNo; }
    public void setErrNo(int errNo) { this.errNo = errNo; }
    public String getErrMsg() { return errMsg; }
    public void setErrMsg(String errMsg) { this.errMsg = errMsg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getTraceID() { return traceID; }
    public void setTraceID(String traceID) { this.traceID = traceID; }
}
