package com.immdream.model.domain.news;

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
 * <p>
 * 新闻评论表
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 新闻
     */
    @TableField("news_id")
    private Integer newsId;

    /**
     * 评论发布人
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 新闻评论
     */
    private String comment;

    /**
     * 0: 未删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 新闻发布时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
