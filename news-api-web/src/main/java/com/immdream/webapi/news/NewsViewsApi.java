package com.immdream.webapi.news;

import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.news.query.NewsQuery;
import com.immdream.model.domain.news.query.NewsTypeQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 新闻浏览功能
 * <p>
 * news com.immdream.webapi.news
 *
 * @author immDream
 * @date 2023/04/28/8:52
 * @since 1.8
 */
@Api("新闻浏览")
public interface NewsViewsApi {
    @ApiOperation("获取新闻")
    JsonResult<Object> getNewsDetails(String id);

    @ApiOperation("获取新闻列表")
    JsonResult<Object> getNewsList();

    @ApiOperation("查询新闻")
    JsonResult<Object> getNewsListByKey(NewsQuery newsQuery);

    @ApiOperation("获取新闻分类")
    JsonResult<Object> getNewsType(NewsTypeQuery newsTypeQuery);

    @ApiOperation("获取新闻分类列表")
    JsonResult<Object> getNewsTypeList();

    @ApiOperation("分页查询新闻")
    JsonResult<Object> queryNewsPage(NewsQuery newsQuery, int pageIndex, int pageSize);

    @ApiOperation("分页查询新闻类型")
    JsonResult<Object> queryNewsTypePage(NewsTypeQuery newsTypeQuery, int pageIndex, int pageSize);
}
