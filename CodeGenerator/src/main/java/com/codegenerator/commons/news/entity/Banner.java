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
    protected Serializable pkVal() {
        return null;
    }

}
