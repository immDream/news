package com.immdream.commons.util;

import com.immdream.commons.exception.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 定义一个前后端交互的
 * <p>
 * news com.immdream.util
 *
 * @author immDream
 * @date 2023/04/11/19:08
 * @since 1.8
 */
@Data
public class JsonResult<T> implements Serializable {
    private Integer status;             // http 状态码
    private String errorCode;           // 错误信息的编码
    private String message;             // 用户提示信息
    private String errorMessage;        // 具体错误提示信息
    private T data;                     // 响应给前端的数据

    /**
     * 根据指定的参数创建一个用于响应的 Json结果
     *
     * @param status       响应的状态码
     * @param message      响应的提示信息
     * @param errorCode    响应的错误编码
     * @param errorMessage 响应的具体错误信息
     * @param data         响应的数据
     * @param <T>
     * @return 返回一个JsonResult对象
     */
    public static <T> JsonResult create(HttpStatus status, String message, String errorCode, String errorMessage, T data) {
        // 创建一个JsonResult对象
        JsonResult jsonResult = new JsonResult();
        // 设置相关的属性
        jsonResult.setStatus(status.value());
        // 设置用户的提示信息
        jsonResult.setMessage(message);
        // 设置错误码
        jsonResult.setErrorCode(errorCode);
        // 设置具体的错误信息
        jsonResult.setErrorMessage(errorMessage);
        // 设置相应的数据
        jsonResult.setData(data);

        return jsonResult;
    }

    /**
     * 根据指定的参数创建一个用于响应的 Json结果
     *
     * @param status       响应的状态码
     * @param message      响应的提示信息
     * @param errorCode    响应的错误编码
     * @param errorMessage 响应的具体错误信息
     * @param <T>
     * @return 返回一个JsonResult对象
     */
    public static <T> JsonResult create(HttpStatus status, String message, String errorCode, String errorMessage) {
        return create(status, message, errorCode, errorMessage, null);
    }

    // public static <T> JsonResult create(HttpStatus status, String message, String errorCode, Exception e, T data) {
    //     return create(status, message, errorCode, e.getMessage(), data);
    // }

    /**
     * 创建一个返回成功信息的 JsonResult对象
     *
     * @param message 响应成功的提示信息
     * @param data    响应的数据
     * @param <T>
     * @return 返回一个 JsonResult对象
     */
    public static <T> JsonResult success(String message, T data) {
        return create(HttpStatus.OK, message, null, null, data);
    }

    /**
     * 创建一个返回错误信息的 JsonResult对象
     * @param errorCode
     * @param message
     * @param <T>
     * @return
     */
    public static <T> JsonResult error (ErrorCode errorCode, String message) {
        return error(errorCode, message);
    }

    public static <T> JsonResult error (ErrorCode errorCode, String message, T data) {
        return create(HttpStatus.OK, message, errorCode.getCode(), errorCode.getMessage(), data);
    }

    /**
     * 创建一个返回成功信息的 JsonResult对象
     *
     * @param data 响应的数据
     * @param <T>
     * @return 返回一个 JsonResult对象
     */
    public static <T> JsonResult success(T data) {
        return success(null, data);
    }

    /**
     * 创建一个返回成功信息的 JsonResult对象
     *
     * @param message 响应成功的提示信息
     * @param <T>
     * @return 返回一个 JsonResult对象
     */
    public static <T> JsonResult success(String message) {
        return success(message, null);
    }

    /**
     * 创建一个返回成功信息的 JsonResult对象
     *
     * @param <T>
     * @return 返回一个 JsonResult对象
     */
    public static <T> JsonResult success() {
        return success(null, null);
    }
}
