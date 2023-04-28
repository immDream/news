package com.immdream.usermanager.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.model.domain.news.Comment;
import com.immdream.usermanager.mapper.CommentMapper;
import com.immdream.usermanager.service.ICommentService;
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
