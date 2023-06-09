package com.immdream.extractnews.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.immdream.extractnews.mapper.NewsTypeMapper;
import com.immdream.extractnews.service.INewsTypeService;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.NewsType;
import com.immdream.model.domain.news.query.NewsTypeQuery;
import com.immdream.model.domain.news.request.NewsTypeDTORequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private NewsTypeMapper newsTypeMapper;

    @Override
    public boolean addNewsType(NewsTypeDTORequest newsTypeDTORequest) {
        if(newsTypeDTORequest == null) {
            log.error("[INFO][新闻类型添加]用户请求参数为空");
            return false;
        }
        NewsType newsType = new NewsType();
        BeanUtils.copyProperties(newsTypeDTORequest, newsType);
        return newsTypeMapper.insert(newsType) == 1;
    }

    @Override
    public boolean deleteNewsType(Integer id) {
        if(id <= 0) return false;
        return new LambdaUpdateChainWrapper<>(newsTypeMapper)
                .set(NewsType::getIsDeleted, true)
                .eq(NewsType::getId, id)
                .update();
    }

    @Override
    public boolean updateNewsType(NewsTypeDTORequest newsTypeDTORequest) {
        int id = newsTypeDTORequest.getId();
        if(id <= 0) return false;
        return new LambdaUpdateChainWrapper<>(newsTypeMapper)
                .set(NewsType::getName, newsTypeDTORequest.getName())
                .eq(NewsType::getId, id)
                .update();
    }

    @Override
    public List<NewsType> getNewsType(NewsTypeQuery newsTypeQuery) {
        if(newsTypeQuery == null) return null;
        Integer id = newsTypeQuery.getId();
        String name = newsTypeQuery.getName();
        // 有id查id
        if (id != null && id > 0) {
            return new LambdaQueryChainWrapper<>(newsTypeMapper)
                    .eq(NewsType::getId, id)
                    .list();
        }
        // 没有id，模糊查询
        if (!StringUtils.isEmpty(name)) {
            return new LambdaQueryChainWrapper<>(newsTypeMapper)
                    .like(NewsType::getName, name)
                    .list();
        }
        return null;
    }

    @Override
    public List<NewsType> getNewsTypeList() {
        return new LambdaQueryChainWrapper<>(newsTypeMapper).list();
    }

    @Override
    public List<NewsType> getNewsTypeList(NewsTypeQuery newsTypeQuery) {
        return null;
    }

    @Override
    public Page<News> getNewTypePage(int pageIndex, int pageSize, NewsTypeQuery newsTypeQuery) {
        return null;
    }
}
