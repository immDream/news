package com.immdream.commons.exception;

import com.immdream.commons.util.JsonResult;
import org.springframework.http.HttpStatus;

/**
 * 定义一个异常信息的错误标识枚举类型 - 阿里规范
 * 错误码：使用 5位字符表示。 00000：表示正确
 * <p>异常错误分类：A B C 三类
 * A：表示用户端错误 - 用户请求数据。
 * B：表示业务逻辑错误。
 * C：表示第三方错误。
 * <p>异常错误级别：使用2位数字表示。
 * 01：表示登录错误。
 * 02：表示注册错误。
 * 04：表示请求错误
 * 05：表示异常错误。
 * <p>具体的异常信息：使用2位数字表示。
 * 00：没有具体信息。
 * 01：表示账号必填。
 * 02：表示账号被禁用。
 * 03：服务端异常。
 * 04：数据异常。
 * <p>错误码：用户请求错误 - A0400；用户请求参数错误 - A0401；用户请求参数无效 - A0402；
 *
 * <p>
 * shop-erp-after com.immdream.product.erp.exception
 *
 * @author immDream
 * @date 2022/08/06/9:40
 * @since 1.8
 */
public enum  ErrorCode {
    // 枚举值
    LOGIN_FAILED("A0100", "用户登录错误"),
    REGISTER_FAILED("A0200", "用户注册错误"),
    LOGIN_CODE_ERROR("A0101", "验证码错误"),
    LOGIN_CODE_EXPIRED("A0102", "验证码已过期"),

    REQUEST_ERROR("A0400", "用户请求错误"),
    REQUEST_PARAM_ERROR("A0401", "用户请求的参数错误"),
    REQUEST_PARAM_INVALID("A0402", "用户请求参数校验错误"),

    // 业务操作
    DATA_NOT_FOUND("B0100", "操作的数据不存在"),
    // 删除的时候正在被使用
    DATA_REFERENCED("B0101", "操作的数据正在被引用"),
    NODE_DEPTH_OUT_RANGE("B0102", "操作的树节点层级超出范围"),
    FILE_IS_EMPTY("B0103", "上传文件为空"),
    FILE_UPLOAD_ERROR("B0104", "文件上传失败"),

    ADMIN_ADD_USER_ERROR("B0204", "管理员添加测试用户操作失败"),

    SERVER_ERROR("A0500", "服务器内部错误"),
    OK("00000", "OK");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误提示信息
     */
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 将错误信息转换为 Json结果
     * @param httpStatus 状态码
     * @param errorMessage 错误信息
     * @param data 响应的数据
     * @param <T>
     * @return
     */
    public <T> JsonResult toJsonResult(HttpStatus httpStatus, String errorMessage, T data) {
        return JsonResult.create(httpStatus, message, code, errorMessage, data);
    }

    /**
     *
     * @param httpStatus 状态码
     * @param data 响应的数据
     * @param <T>
     * @return
     */
    public <T>JsonResult toJsonResult(HttpStatus httpStatus, T data) {
        return toJsonResult(httpStatus,  "", data);
    }

    /**
     * 将错误信息转换为状态码为 200的Json结果
     * @param errorMessage
     * @param data
     * @param <T>
     * @return
     */
    public <T>JsonResult toJsonResult(String errorMessage, T data) {
        return toJsonResult(HttpStatus.OK, errorMessage, data);
    }

    /**
     * 将错误信息转换为状态码为 200的Json结果
     * @param data
     * @param <T>
     * @return
     */
    public <T>JsonResult toJsonResult(T data) {
        return toJsonResult(HttpStatus.OK, "", data);
    }
}
