package com.immdream.usermanager.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin")
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员账户，已存在 ROOT用户
     */
    @TableField("admin_username")
    private String adminUsername;

    /**
     * 管理员账户密码
     */
    @TableField("admin_password")
    private String adminPassword;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 管理员账户权限：0为空权限
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 创建用户时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新用户时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
