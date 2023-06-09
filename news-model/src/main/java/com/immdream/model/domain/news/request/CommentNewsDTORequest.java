package com.immdream.model.domain.news.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 新闻评论
 * <p>
 * news com.immdream.model.domain.news.request
 *
 * @author immDream
 * @date 2023/05/28/10:11
 * @since 1.8
 */
@Data
public class CommentNewsDTORequest {

    /**
     * 新闻
     */
    @NotNull(groups = {BaseDTO.Insert.class})
    private Integer newsId;

    /**
     * 评论用户
     */
    @NotNull(groups = {BaseDTO.Insert.class})
    private Integer userId;

    /**
     * 新闻评论
     */
    @NotEmpty(groups = {BaseDTO.Insert.class})
    private String comment;
}
