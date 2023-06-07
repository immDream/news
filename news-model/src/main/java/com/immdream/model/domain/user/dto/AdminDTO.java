package com.immdream.model.domain.user.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.user.dto
 *
 * @author immDream
 * @date 2023/05/08/20:28
 * @since 1.8
 */
@Data
public class AdminDTO {
    private Integer id;

    /**
     * 管理员账户，已存在 ROOT用户
     */
    private String adminUsername;

    /**
     * 管理员账户密码
     */
    private String adminPassword;


    /**
     * 管理员账户权限：0为空权限
     */
    private Integer roleId;
}
