package com.codegenerator.commons.news.service.impl;

import com.codegenerator.commons.news.entity.News;
import com.codegenerator.commons.news.mapper.NewsMapper;
import com.codegenerator.commons.news.service.INewsService;
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
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
