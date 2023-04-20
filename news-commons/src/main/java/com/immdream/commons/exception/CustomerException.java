package com.immdream.commons.exception;

/**
 * 定义一个用户端异常信息
 * <p>
 * shop-erp-after com.immdream.product.erp.exception
 *
 * @author immDream
 * @since 1.8
 */
public class CustomerException extends BaseException{
    public CustomerException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CustomerException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
