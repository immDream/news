package com.codegenerator.commons.news.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
     * 管理员账户，已存在ROOT用户
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
    protected Serializable pkVal() {
        return null;
    }

}
