package com.immdream.model.domain.user;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_browsing_history")
public class UserBrowsingHistory extends Model<UserBrowsingHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 新闻id
     */
    @TableField("news_id")
    private String newsId;

    @TableField("delete_id")
    private Boolean deleteId;

    /**
     * 浏览时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 数据更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
