package com.immdream.model.domain.user.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 更新用户信息的请求类
 * <p>
 * news com.immdream.model.domain.user.request
 *
 * @author immDream
 * @date 2023/04/16/19:29
 * @since 1.8
 */
@Data
@ToString
public class UserUpdateDTORequest extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -3901053703700174432L;

    @NotNull(groups = {Update.class}, message = "用户id不能为空")
    private Integer id;

    /**
     * 用户账户,不超过16字符
     */
    @NotNull(groups = {Insert.class}, message = "用户名必填")
    private String username;

    /**
     * 用户昵称
     */
    @NotEmpty(groups = {Insert.class, Update.class}, message = "用户昵称不能为空")
    private String nickname;

    /**
     * 用户邮箱
     */
    @NotEmpty(groups = {Insert.class, Update.class}, message = "用户邮箱不能为空")
    private String email;

    /**
     * 用户手机号
     */
    // @NotEmpty(groups = {Insert.class, Update.class}, message = "用户手机号不能为空")
    // private String telephone;

    /**
     * 用户头像，默认相对地址
     */
    // private String image;

    /**
     * 用户描述
     */
    private String describe;

}
