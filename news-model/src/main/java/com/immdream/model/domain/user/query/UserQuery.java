package com.immdream.model.domain.user.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 定义一个用来接收偏僻查询请求参数的类型
 * <p>
 * news com.immdream.model.domain.user.query
 *
 * @author immDream
 * @date 2023/04/16/22:22
 * @since 1.8
 */
@Data
public class UserQuery implements Serializable {

    private static final long serialVersionUID = 7442020310912479777L;

    /**
     * 查询用户名称
     */
    private String username;

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
}
