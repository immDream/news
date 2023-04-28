package com.immdream.model.domain.news;

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
 * @since 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news")
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 新闻标题
     */
    @TableField("news_title")
    private String newsTitle;

    /**
     * 新闻内容
     */
    @TableField("news_content")
    private String newsContent;

    /**
     * 作者，默认为注销账户
     */
    private Integer author;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 新闻标题图片地址路径,命名方式为news_id + # + timestamp
     */
    @TableField("news_image_path")
    private String newsImagePath;

    /**
     * 新闻原地址
     */
    @TableField("news_link")
    private String newsLink;

    /**
     * 新闻模块 category_id
     */
    @TableField("news_modules")
    private Integer newsModules;

    /**
     * 点赞数
     */
    @TableField("joke_count")
    private Integer jokeCount;

    /**
     * 收藏数
     */
    @TableField("collection_count")
    private Integer collectionCount;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 浏览数
     */
    @TableField("visit_count")
    private Integer visitCount;

    /**
     * 0: 未置顶，1: 置顶
     */
    @TableField("is_top")
    private Boolean isTop;

    /**
     * 0: 未删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 0: 未过期
     */
    @TableField("is_expired")
    private Boolean isExpired;

    /**
     * 新闻标签，用分号分割
     */
    @TableField("news_tags")
    private String newsTags;

    /**
     * 新闻发布时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 新闻更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
