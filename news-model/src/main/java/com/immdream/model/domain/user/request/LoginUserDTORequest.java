package com.immdream.model.domain.user.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 角色登录
 * <p>
 * news com.immdream.model.domain.user.request
 *
 * @author immDream
 * @date 2023/04/13/16:41
 * @since 1.8
 */
@Data
@ToString
public class LoginUserDTORequest {
    @NotNull(groups = {BaseDTO.Select.class}, message = "用户名必填")
    @Pattern(regexp = "^.{8,16}$", message = "用户名长度在8-16字符之间")
    String username;

    @NotNull(groups = {BaseDTO.Select.class}, message = "手机号必填")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",message = "手机号格式不对")
    String telephone;

    @NotNull(groups = {BaseDTO.Select.class}, message = "密码必填")
    @Pattern(regexp = "^[a-zA-Z]\\w{5,15}$",message = "密码必须以字母开头，长度在6~16之间，只能包含字母、数字和下划线")
    String password;

    @NotNull(groups = {BaseDTO.Select.class}, message = "验证码必填")
    String verifyCode;
}
