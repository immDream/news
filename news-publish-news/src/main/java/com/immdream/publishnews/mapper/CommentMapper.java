package com.immdream.publishnews.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.immdream.model.domain.news.Comment;
import com.immdream.publishnews.domain.NewsCommentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<NewsCommentDTO> getNewsCommentList(@Param("newsid") Integer newsId);
}
