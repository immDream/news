package com.immdream.usermanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.usermanager.entity.NewsType;
import com.immdream.usermanager.mapper.NewsTypeMapper;
import com.immdream.usermanager.service.INewsTypeService;
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
