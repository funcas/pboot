package com.funcas.pboot.common.exception;


/**
 * 未认证异常，该异常是为 spring mvc 异常方法签名提供的异常，当出现该异常时，会自动跳转到登录页面。
 *
 */
public class UnautherizedException extends ServiceException {

    /**
     * 未认证异常，该异常是为 spring mvc 异常方法签名提供的异常
     */
    public UnautherizedException() {
        super();
    }

    /**
     * 未认证异常，该异常是为 spring mvc 异常方法签名提供的异常
     *
     * @param message 异常信息
     */
    public UnautherizedException(String message) {
        super(message);
    }

    /**
     * 未认证异常，该异常是为 spring mvc 异常方法签名提供的异常
     *
     * @param cause 异常类
     */
    public UnautherizedException(Throwable cause) {
        super(cause);
    }

    /**
     * 未认证异常，该异常是为 spring mvc 异常方法签名提供的异常
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public UnautherizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
