package com.codegenerator.commons.news.service.impl;

import com.codegenerator.commons.news.entity.NewsComment;
import com.codegenerator.commons.news.mapper.NewsCommentMapper;
import com.codegenerator.commons.news.service.INewsCommentService;
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
public class NewsCommentServiceImpl extends ServiceImpl<NewsCommentMapper, NewsComment> implements INewsCommentService {

}
