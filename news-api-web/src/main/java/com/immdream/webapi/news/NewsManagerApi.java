package com.immdream.webapi.news;

import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.news.query.NewsQuery;
import com.immdream.model.domain.news.query.NewsTypeQuery;
import com.immdream.model.domain.news.request.AddNewsDTORequest;
import com.immdream.model.domain.news.request.NewsTypeDTORequest;
import com.immdream.model.domain.news.request.DeleteNewsDTORequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 新闻管理服务定义
 * <p>
 * news com.immdream.webapi.news
 *
 * @author immDream
 * @date 2023/03/13/16:26
 * @since 1.8
 */
@Api(value = "新闻管理")
public interface NewsManagerApi {

    @ApiOperation("添加新闻")
    JsonResult<Object> addNews(AddNewsDTORequest addNewsDTORequest);

    @ApiOperation("删除新闻")
    JsonResult<Object> deleteNews(Integer id);

    @ApiOperation("新闻置顶")
    JsonResult<Object> upNews(Integer id);

    @ApiOperation("取消置顶")
    JsonResult<Object> downNews(Integer id);

    @ApiOperation("新闻分类添加")
    JsonResult<Object> addNewsType(NewsTypeDTORequest newsTypeDTORequest);

    @ApiOperation("新闻分类修改")
    JsonResult<Object> updateNewsType(NewsTypeDTORequest newsTypeDTORequest);

    @ApiOperation("新闻分类删除")
    JsonResult<Object> deleteNewsType(Integer id);

    @ApiOperation("获取新闻")
    JsonResult<Object> getNewsDetails(Integer id);

    @ApiOperation("获取新闻列表")
    JsonResult<Object> getNewsList();

    @ApiOperation("查询新闻")
    JsonResult<Object> getNewsListByKey(String title);

    @ApiOperation("获取新闻分类")
    JsonResult<Object> getNewsType(NewsTypeQuery newsTypeQuery);

    @ApiOperation("获取新闻分类列表")
    JsonResult<Object> getNewsTypeList();

    @ApiOperation("分页查询新闻")
    JsonResult<Object> queryNewsPage(NewsQuery newsQuery, int pageIndex, int pageSize);

    @ApiOperation("分页查询新闻类型")
    JsonResult<Object> queryNewsTypePage(NewsTypeQuery newsTypeQuery, int pageIndex, int pageSize);

    @ApiOperation("获取新闻评论列表")
    JsonResult<Object> getNewsCommentList();

    @ApiOperation("根据条件查找评论")
    JsonResult<Object> getNewsCommentListByCondition(String condition, String key);

    @ApiOperation("封禁评论")
    JsonResult<Object> banComment(Integer id);

    @ApiOperation("解封评论")
    JsonResult<Object> unBanComment(Integer id);

}
