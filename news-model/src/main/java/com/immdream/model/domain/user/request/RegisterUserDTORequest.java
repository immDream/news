package com.immdream.model.domain.user.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
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
    private String username;

    /**
     * 账户密码(必须包含一个字母及数字)
     */
    @NotEmpty(groups = {Insert.class}, message = "用户密码必填")
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    @NotEmpty(groups = {Insert.class}, message = "手机号必填")
    private String telephone;

    /**
     * 用户头像，默认相对地址
     */
    private String image;

    /**
     * 用户描述
     */
    private String describe;
}
