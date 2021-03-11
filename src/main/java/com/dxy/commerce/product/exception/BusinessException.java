package com.dxy.commerce.product.exception;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/27 11:42 下午
 */
public class BusinessException extends RuntimeException {

    protected String message;

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
