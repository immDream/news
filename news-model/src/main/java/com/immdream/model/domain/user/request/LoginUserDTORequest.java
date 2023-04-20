package com.immdream.model.domain.user.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

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
    @NotNull(groups = {BaseDTO.Insert.class}, message = "用户名必填")
    String username;
    @NotNull(groups = {BaseDTO.Insert.class}, message = "密码必填")
    String password;
    @NotNull(groups = {BaseDTO.Insert.class}, message = "验证码必填")
    String verifyCode;
}
