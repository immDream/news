package com.immdream.model.domain.news;

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
 * 某一新闻的评论表
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
     * 评论发布人 id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 新闻评论 id
     */
    @TableField("comment_id")
    private String commentId;

    /**
     * 新闻发布时间
     */
    @TableField("create_time")
    private Date createTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
