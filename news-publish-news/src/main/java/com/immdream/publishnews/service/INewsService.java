package com.immdream.publishnews.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.dto.HotNewsDTO;
import com.immdream.model.domain.news.query.NewsDetailsQuery;
import com.immdream.model.domain.news.query.NewsQuery;
import com.immdream.model.domain.news.request.AddNewsDTORequest;
import com.immdream.model.domain.news.request.DeleteNewsDTORequest;
import com.immdream.publishnews.mapper.NewsMapper;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface INewsService extends IService<News> {

    /**
     * 热点新闻列表
     * @return
     */
    List<HotNewsDTO> hotNewsList();

    /**
     * 添加新闻
     * @param addNewsDTORequest
     * @return
     */
    boolean addNews(AddNewsDTORequest addNewsDTORequest);

    /**
     * 根据条件删除新闻
     * @param id
     * @return
     */
    boolean deleteNews(Integer id);

    /**
     * 更新新闻
     * @param news
     * @return
     */
    boolean updateNewsCount(News news);

    /**
     * 新闻点赞
     * @param newsDetailsQuery
     * @return
     */
    boolean jokeNews(NewsDetailsQuery newsDetailsQuery);

    /**
     * 新闻置顶
     * @param id
     * @return
     */
    boolean topNews(Integer id, boolean isUp);

    /**
     * 查询新闻
     * @param id
     * @return
     */
    News getNewsDetails(Integer id);

    /**
     * 查询新闻并记录用户行为
     * @param userId
     * @param id
     * @return
     */
    News getNewsDetails(Integer userId, Integer id);

    /**
     * 查询新闻列表
     * @return
     */
    List<News> getNewsList();

    /**
     * 根据条件查询新闻列表
     * @param newsQuery
     * @return
     */
    List<News> getNewsList(NewsQuery newsQuery);

    /**
     * 分页查询新闻
     * @param pageIndex 当前页
     * @param pageSize  每页最大数
     * @param newsQuery
     * @return
     */
    Page<News> getNewsPage(int pageIndex, int pageSize, NewsQuery newsQuery);
}
