package com.funcas.pboot.common.exception;

/**
 * 业务逻辑异常类,执行业务错误时抛出
 *
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

    /**
     * 业务逻辑异常类,执行业务错误时抛出
     */
    public ServiceException() {
        super();
    }

    /**
     * 业务逻辑异常类,执行业务错误时抛出
     *
     * @param message 异常信息
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 业务逻辑异常类,执行业务错误时抛出
     *
     * @param cause 异常类
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * 业务逻辑异常类,执行业务错误时抛出
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
