package com.immdream.model.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.user
 *
 * @author immDream
 * @date 2023/05/30/13:58
 * @since 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_region")
public class UserRegion extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer roleId;

    @TableField("user_id")
    private Integer userId;

    @TableField("region")
    private String region;

    /**
     * 赋予用户权限
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更改用户权限
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;


    @Override
    public Serializable pkVal() {
        return this.roleId;
    }
}
