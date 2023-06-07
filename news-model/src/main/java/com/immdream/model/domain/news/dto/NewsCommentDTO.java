package com.immdream.model.domain.news.dto;


/**
 * 返回给前端的评论
 * <p>
 * news com.immdream.model.domain.news.dto
 *
 * @author immDream
 * @date 2023/05/28/10:14
 * @since 1.8
 */
public class NewsCommentDTO {
    /**
     * 新闻
     */
    private String id;

    /**
     * 评论用户
     */
    private Integer userId;

    /**
     * 新闻评论
     */
    private String comment;
}
