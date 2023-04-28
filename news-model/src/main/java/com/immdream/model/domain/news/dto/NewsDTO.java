package com.immdream.model.domain.news.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.news.dto
 *
 * @author immDream
 * @date 2023/04/27/12:54
 * @since 1.8
 */
@Data
public class NewsDTO implements Serializable {

    private static final long serialVersionUID = -9219997460653678755L;
    private String id;
    /**
     * 新闻标题
     */
    private String newsTitle;

    /**
     * 新闻内容
     */
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
    private String newsImagePath;

    /**
     * 新闻原地址
     */
    private String newsLink;

    /**
     * 新闻模块 category_id
     */
    private Integer newsModules;

    /**
     * 点赞数
     */
    private Integer jokeCount;

    /**
     * 收藏数
     */
    private Integer collectionCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 浏览数
     */
    private Integer visitCount;

    /**
     * 0: 未置顶，1: 置顶
     */
    private Boolean isTop;

    /**
     * 0: 未删除
     */
    private Boolean isDeleted;

    /**
     * 0: 未过期
     */
    private Boolean isExpired;

    /**
     * 新闻标签，用分号分割
     */
    private String newsTags;

    /**
     * 新闻发布时间
     */
    private Date createTime;

    /**
     * 新闻更新时间
     */
    private Date updateTime;
}
