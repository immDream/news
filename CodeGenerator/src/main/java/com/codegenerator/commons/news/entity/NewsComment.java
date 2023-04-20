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
@TableName("news_comment")
public class NewsComment extends Model<NewsComment> {

    private static final long serialVersionUID = 1L;

    /**
     * 评论发布人
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 新闻评论
     */
    @TableField("comment_id")
    private String commentId;

    /**
     * 新闻发布时间
     */
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
