package com.stockwise.common;

import org.slf4j.MDC;

/**
 * 统一响应工厂：success/error 均写入当前请求的 TraceID（从 MDC 获取）。
 */
public final class ApiResponses {

    private static final String TRACE_ID_KEY = "traceId";

    public static <T> ApiResponse<T> success(T data) {
        String traceId = MDC.get(TRACE_ID_KEY);
        return ApiResponse.of(ErrNo.SUCCESS, "", data, traceId != null ? traceId : "");
    }

    public static <T> ApiResponse<T> error(int errNo, String errMsg) {
        String traceId = MDC.get(TRACE_ID_KEY);
        return ApiResponse.of(errNo, errMsg != null ? errMsg : "", null, traceId != null ? traceId : "");
    }

    private ApiResponses() {}
}
