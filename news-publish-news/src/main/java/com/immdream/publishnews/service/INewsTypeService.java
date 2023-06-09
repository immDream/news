package com.immdream.publishnews.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.NewsType;
import com.immdream.model.domain.news.query.NewsTypeQuery;
import com.immdream.model.domain.news.request.NewsTypeDTORequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface INewsTypeService extends IService<NewsType> {

    /**
     * 添加新闻类型
     * @param newsTypeDTORequest
     * @return
     */
    boolean addNewsType(NewsTypeDTORequest newsTypeDTORequest);

    /**
     * 删除新闻类型
     * @return
     */
    boolean deleteNewsType(Integer id);

    /**
     * 修改新闻类型
     * @param newsTypeDTORequest
     * @return
     */
    boolean updateNewsType(NewsTypeDTORequest newsTypeDTORequest);

    /**
     * 查询新闻类型
     * @param id
     * @return
     */
    NewsType getNewsType(Integer id);

    /**
     * 查询全部新闻类型
     * @return
     */
    List<NewsType> getNewsTypeList();

    /**
     * 查询全部新闻类型
     * @param newsTypeQuery
     * @return
     */
    List<NewsType> getNewsTypeList(NewsTypeQuery newsTypeQuery);

    /**
     * 分页查询新闻类型
     * @param pageIndex 当前页
     * @param pageSize  每页最大数
     * @param newsTypeQuery
     * @return
     */
    Page<News> getNewTypePage(int pageIndex, int pageSize, NewsTypeQuery newsTypeQuery);
}
