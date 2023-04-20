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
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

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
    private String imgae;

    /**
     * 用户描述
     */
    private String describe;

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
