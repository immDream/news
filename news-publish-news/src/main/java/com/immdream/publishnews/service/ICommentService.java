package com.immdream.publishnews.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.immdream.model.domain.news.Comment;
import com.immdream.publishnews.domain.NewsCommentDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 发布评论并修改评论数
     * @param comment
     * @return
     */
    boolean saveComment(Comment comment);

    List<Comment> getCommentList();

    List<Comment> getNewsCommentList(Integer id);

    List<NewsCommentDTO> getNewsAndUserCommentList(Integer id);

    boolean banComment(Integer id);

    boolean unBanComment(Integer id);
}
