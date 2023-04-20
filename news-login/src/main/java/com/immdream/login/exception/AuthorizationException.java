package com.immdream.login.exception;

import com.immdream.commons.exception.BaseException;
import com.immdream.commons.exception.ErrorCode;

/**
 * 账户认证异常
 * <p>
 * news com.immdream.login.exception
 *
 * @author immDream
 * @date 2023/04/12/10:00
 * @since 1.8
 */
public class AuthorizationException extends BaseException {
    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorizationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
