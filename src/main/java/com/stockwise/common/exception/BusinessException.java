package com.stockwise.common.exception;

import com.stockwise.common.ErrNo;

/**
 * 业务异常，带 errNo/errMsg，由 GlobalExceptionHandler 转换为 ApiResponse。
 */
public class BusinessException extends RuntimeException {

    private final int errNo;
    private final String errMsg;

    public BusinessException(int errNo, String errMsg) {
        super(errMsg);
        this.errNo = errNo;
        this.errMsg = errMsg;
    }

    public int getErrNo() { return errNo; }
    public String getErrMsg() { return errMsg; }
}
