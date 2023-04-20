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
@TableName("banner")
public class Banner extends Model<Banner> {

    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private String userId;

    @TableField("articel_id")
    private String articelId;

    private String title;

    @TableField("img_url")
    private String imgUrl;

    @TableField("article_url")
    private String articleUrl;

    private Integer pageViews;

    @TableField("up_time")
    private Date upTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
