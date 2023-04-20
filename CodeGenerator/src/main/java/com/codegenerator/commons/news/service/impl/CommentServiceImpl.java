package com.codegenerator.commons.news.service.impl;

import com.codegenerator.commons.news.entity.Comment;
import com.codegenerator.commons.news.mapper.CommentMapper;
import com.codegenerator.commons.news.service.ICommentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
