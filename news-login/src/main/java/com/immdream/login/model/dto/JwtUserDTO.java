package com.immdream.login.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.login.model.dto
 *
 * @author immDream
 * @date 2023/04/13/23:20
 * @since 1.8
 */
@Data
@ApiModel(description = "校验用户类")
public class JwtUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户账户,不超过16字符
     */
    @ApiModelProperty(value = "用户名")
    @NotBlank
    private String username;

    /**
     * 账户密码(必须包含一个字母及数字)
     */
    @ApiModelProperty(value = "密码")
    @NotBlank
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;
}