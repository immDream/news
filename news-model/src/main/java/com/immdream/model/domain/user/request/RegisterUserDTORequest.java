package com.immdream.model.domain.user.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户注册数据传输类
 * <p>
 * news com.immdream.model.domain.user.request
 *
 * @author immDream
 * @date 2023/04/16/22:02
 * @since 1.8
 */
@Data
public class RegisterUserDTORequest extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 4242460344237807507L;

    /**
     * 用户账户,不超过16字符
     */
    @NotEmpty(groups = {Insert.class}, message = "用户名必填")
    @Pattern(regexp = "^.{8,16}$", message = "用户名长度在8-16字符之间")
    private String username;

    /**
     * 账户密码(必须包含一个字母及数字)
     */
    @NotEmpty(groups = {Insert.class}, message = "用户密码必填")
    @Pattern(regexp = "^[a-zA-Z]\\w{5,15}$",message = "密码必须以字母开头，长度在6~16之间，只能包含字母、数字和下划线")
    private String password;

    /**
     * 账户密码(必须包含一个字母及数字)
     */
    @NotEmpty(groups = {Insert.class}, message = "用户密码必填")
    private String password2;

    /**
     * 用户昵称
     */
    @Pattern(regexp = "^.{0,20}$", message = "用户名长度在0-20字符之间")
    private String nickname;

    /**
     * 用户邮箱
     */
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱格式不对")
    private String email;

    /**
     * 用户手机号
     */
    @NotEmpty(groups = {Insert.class}, message = "手机号必填")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",message = "手机号格式不对")
    private String telephone;
}
