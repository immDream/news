package com.immdream.commons.exception;

/**
 * 定义一个业务端异常信息
 * <p>
 *
 * @author immDream
 * @since 1.8
 */
public class ServiceException extends BaseException{
    public ServiceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
