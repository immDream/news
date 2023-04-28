package com.immdream.publishnews.controller;

import com.github.pagehelper.Page;
import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.commons.util.PageResult;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.NewsType;
import com.immdream.model.domain.news.query.NewsQuery;
import com.immdream.model.domain.news.query.NewsTypeQuery;
import com.immdream.model.domain.news.request.AddNewsDTORequest;
import com.immdream.model.domain.news.request.DeleteNewsDTORequest;
import com.immdream.model.domain.news.request.NewsTypeDTORequest;
import com.immdream.publishnews.service.INewsService;
import com.immdream.publishnews.service.INewsTypeService;
import com.immdream.webapi.news.NewsManagerApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 新闻管理服务
 * <p>
 * news com.immdream.publishnews.controller
 *
 * @author immDream
 * @date 2023/04/27/11:55
 * @since 1.8
 */
@Slf4j
@RestController
@RequestMapping("/news")
public class NewsManagerController implements NewsManagerApi {

    @Autowired
    private INewsService newsService;

    @Autowired
    private INewsTypeService newsTypeService;

    /**
     * 管理员添加新闻
     * @param addNewsDTORequest
     * @return
     */
    @Override
    @PostMapping("/addNews")
    public JsonResult addNews(@RequestBody AddNewsDTORequest addNewsDTORequest) {
        if(addNewsDTORequest == null) {
            log.info("[INFO][新闻添加]新闻添加失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        boolean isFinish = newsService.addNews(addNewsDTORequest);
        if(!isFinish) {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻增加失败!");
        }
        log.info("[INFO][新闻添加]新闻添加成功!");
        return JsonResult.success("新闻增加成功!");
    }

    @Override
    @DeleteMapping("/deleteNews/{id}")
    public JsonResult deleteNews(@PathVariable String id) {
        if(id == null) {
            log.info("[INFO][新闻删除]新闻删除失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        boolean isFinish = newsService.deleteNews(id);
        if(!isFinish) {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻删除失败!");
        }
        log.info("[INFO][新闻删除]新闻删除成功!");
        return JsonResult.success("新闻删除成功!", id);
    }

    @Override
    @PutMapping("/topNews/{id}")
    public JsonResult topNews(@PathVariable Integer id) {
        if(id == null) {
            log.info("[INFO][新闻置顶]新闻置顶失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        boolean isFinish = newsService.topNews(id);
        if(!isFinish) {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻置顶失败!");
        }
        log.info("[INFO][新闻置顶]新闻置顶成功!");
        return JsonResult.success("新闻置顶成功!", id);
    }

    @Override
    @PostMapping("/addNewsType")
    public JsonResult addNewsType(@RequestBody NewsTypeDTORequest newsTypeDTORequest) {
        if(newsTypeDTORequest == null) {
            log.info("[INFO][新闻分类添加]新闻分类添加失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        boolean isFinish = newsTypeService.addNewsType(newsTypeDTORequest);
        if(!isFinish) {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻分类添加失败!");
        }
        log.info("[INFO][新闻分类添加]新闻分类添加成功!");
        return JsonResult.success("新闻分类添加增加成功!");
    }

    @Override
    @PutMapping("/updateNewsType")
    public JsonResult updateNewsType(@RequestBody NewsTypeDTORequest newsTypeDTORequest) {
        if(newsTypeDTORequest == null) {
            log.info("[INFO][新闻类型更新]新闻类型更新失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        boolean isFinish = newsTypeService.deleteNewsType(newsTypeDTORequest.getId());
        if(!isFinish) {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻类型更新失败!");
        }
        log.info("[INFO][新闻类型更新]新闻类型更新成功!");
        return JsonResult.success("新闻类型更新成功!", newsTypeDTORequest.getId());
    }

    @Override
    @DeleteMapping("/deleteNewsType/{id}")
    public JsonResult deleteNewsType(@PathVariable Integer id) {
        if(id == null) {
            log.info("[INFO][新闻类型删除]新闻类型删除失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        boolean isFinish = newsTypeService.deleteNewsType(id);
        if(!isFinish) {
            return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻类型删除失败!");
        }
        log.info("[INFO][新闻类型删除]新闻类型删除成功!");
        return JsonResult.success("新闻类型删除成功!", id);
    }

    @Override
    @GetMapping("/getNews/{id}")
    public JsonResult getNewsDetails(@PathVariable String id) {
        if(StringUtils.isEmpty(id)) {
            log.info("[INFO][新闻详情]新闻详情查看失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        News news = newsService.getNewsDetails(id);
        if(news == null) {
            log.info("[INFO][新闻详情]新闻详情查看失败!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "该新闻不存在！");
        }
        log.info("[INFO][新闻详情]新闻详情查看成功!");
        return JsonResult.success(news);
    }

    @Override
    @GetMapping("/getNewsAll")
    public JsonResult getNewsList() {
        List<News> newsList = newsService.getNewsList();
        log.info("[INFO][获取新闻列表]获取新闻列表!");
        return JsonResult.success(newsList);
    }

    @Override
    @GetMapping("/queryNews")
    public JsonResult getNewsListByKey(@RequestBody NewsQuery newsQuery) {
        // String id = newsQuery.getId();
        // if(StringUtils.isEmpty(id)) {
        //     log.info("[INFO][搜索新闻]搜索新闻失败，请求参数为空!");
        //     return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        // }
        List<News> newsList = newsService.getNewsList(newsQuery);
        if(newsList == null) {
            log.info("[INFO][搜索新闻]搜索新闻失败!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "该新闻不存在！");
        }
        log.info("[INFO][搜索新闻]搜索新闻成功!");
        return JsonResult.success(newsList);
    }

    @Override
    @GetMapping("/queryNewsType")
    public JsonResult getNewsType(@RequestBody NewsTypeQuery newsTypeQuery) {
        if(newsTypeQuery == null) {
            log.info("[INFO][查询新闻类型]查询新闻类型失败，参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户参数为空!");
        }
        List<NewsType> newsTypes = newsTypeService.getNewsType(newsTypeQuery);
        if(newsTypes == null) {
            log.info("[INFO][查询新闻类型]查询新闻类型失败，没有对应的类型!");
            return JsonResult.error(ErrorCode.SERVER_ERROR, "查询失败，没有对应数据!");
        }
        return JsonResult.success(newsTypes);
    }

    @Override
    @GetMapping("/getNewsTypeList")
    public JsonResult getNewsTypeList() {
        List<NewsType> newsTypes = newsTypeService.getNewsTypeList();
        if(newsTypes == null) {
            log.info("[INFO][新闻类型列表]新闻类型列表为空");
        }
        return JsonResult.success(newsTypes);
    }

    @Override
    @GetMapping("/queryNewsPage")
    public JsonResult queryNewsPage(@RequestBody NewsQuery newsQuery,
                                    @RequestParam(defaultValue = "1") int pageIndex,
                                    @RequestParam(defaultValue = "10") int pageSize) {
        Page<News> publishNewsPage = newsService.getNewsPage(pageIndex, pageSize, newsQuery);
        if(publishNewsPage == null) {
            if(newsQuery.getAuthor() == 0) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户不存在！");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数错误！");
        }
        log.info("[INFO][查询新闻列表]获取新闻列表成功!");
        // 分页数据转换
        PageResult<News> pageResult = PageResult.create(publishNewsPage);
        return JsonResult.success(pageResult);
    }

    @Override
    @GetMapping("/queryNewsTypePage")
    public JsonResult queryNewsTypePage(@RequestBody NewsTypeQuery newsTypeQuery,
                                        @RequestParam(defaultValue = "1") int pageIndex,
                                        @RequestParam(defaultValue = "10") int pageSize) {

        return null;
    }
}
