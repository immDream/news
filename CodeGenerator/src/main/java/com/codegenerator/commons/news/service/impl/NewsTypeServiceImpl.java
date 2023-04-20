package com.codegenerator.commons.news.service.impl;

import com.codegenerator.commons.news.entity.NewsType;
import com.codegenerator.commons.news.mapper.NewsTypeMapper;
import com.codegenerator.commons.news.service.INewsTypeService;
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
public class NewsTypeServiceImpl extends ServiceImpl<NewsTypeMapper, NewsType> implements INewsTypeService {

}
