package com.immdream.model.domain.user.dto;

import lombok.Data;
import lombok.ToString;


/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.user.dto
 *
 * @author immDream
 * @date 2023/04/16/23:13
 * @since 1.8
 */
@Data
@ToString
public class UserDTO {
    /**
     * 用户 id
     */
    private Integer id;

    /**
     * 用户账户,不超过16字符
     */
    private String username;

    /**
     * 账户密码(必须包含一个字母及数字)
     */
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
