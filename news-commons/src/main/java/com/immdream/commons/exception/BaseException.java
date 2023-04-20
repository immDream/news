package com.immdream.commons.exception;

import com.immdream.commons.util.JsonResult;
import org.springframework.http.HttpStatus;

/**
 * 自定义异常的父类
 * <p>
 * shop-erp-after com.immdream.product.erp.exception
 *
 * @author immDream
 * @date 2022/08/06/10:03
 * @since 1.8
 */
public class BaseException extends RuntimeException{

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    /**
     * 根据错误码创建一个自定义异常
     * @param errorCode
     */
    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 根据错误码和异常创建一个自定义异常
     * @param errorCode
     * @param cause
     */
    public BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "错误码：" + errorCode.getCode() + "; 错误信息：" + super.toString();
    }

    /**
     * 将异常信息转换为一个Json结果
     * @param httpStatus 响应的状态码
     * @param data 响应的数据
     * @param <T>
     * @return  返回一个JsonResult对象
     */
    public <T> JsonResult<T> toJsonResult(HttpStatus httpStatus, T data) {
        return JsonResult.create(httpStatus, errorCode.getMessage(), errorCode.getCode(), "", data);
    }

    /**
     * 根据状态码转换为一个Json结果
     * @param httpStatus
     * @param <T>
     * @return
     */
    public <T>JsonResult<T> toJsonResult(HttpStatus httpStatus) {
        return toJsonResult(httpStatus, null);
    }

    public <T>JsonResult<T> toJsonResult(T data) {
        return toJsonResult(HttpStatus.OK, data);
    }

    public <T>JsonResult<T> toJsonResult() {
        return toJsonResult(HttpStatus.OK, null);
    }
}
