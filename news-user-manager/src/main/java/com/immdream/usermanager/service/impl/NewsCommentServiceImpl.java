package com.immdream.usermanager.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.model.domain.news.NewsComment;
import com.immdream.usermanager.mapper.NewsCommentMapper;
import com.immdream.usermanager.service.INewsCommentService;
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
public class NewsCommentServiceImpl extends ServiceImpl<NewsCommentMapper, NewsComment> implements INewsCommentService {

}
