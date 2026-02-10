package com.stockwise.common;

/**
 * 与《StockWise 前后端开发范式》错误码一致，Controller/Service/全局异常处理统一使用。
 */
public final class ErrNo {

    public static final int SUCCESS = 0;
    public static final int PARAM_INVALID = 40001;
    public static final int BUSINESS_RULE = 40002;
    public static final int NOT_FOUND = 40004;
    public static final int UNAUTHENTICATED = 41001;
    public static final int FORBIDDEN = 43001;
    public static final int INTERNAL_ERROR = 50000;
    public static final int DEPENDENCY_ERROR = 50001;

    private ErrNo() {}
}
