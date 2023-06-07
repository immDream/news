package com.immdream.publishnews.controller;

import com.github.pagehelper.Page;
import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.commons.util.PageResult;
import com.immdream.commons.util.RedisUtil;
import com.immdream.model.domain.BaseDTO;
import com.immdream.model.domain.news.Comment;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.NewsType;
import com.immdream.model.domain.news.dto.HotNewsDTO;
import com.immdream.model.domain.news.query.NewsDetailsQuery;
import com.immdream.model.domain.news.query.NewsQuery;
import com.immdream.model.domain.news.query.NewsTypeQuery;
import com.immdream.model.domain.news.request.CommentNewsDTORequest;
import com.immdream.publishnews.domain.NewsCommentDTO;
import com.immdream.publishnews.domain.NewsPicDTO;
import com.immdream.publishnews.service.*;
import com.immdream.webapi.news.NewsViewsApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 新闻发布服务
 * <p>
 * news com.immdream.publishnews.controller
 *
 * @author immDream
 * @date 2023/04/27/11:55
 * @since 1.8
 */
@Slf4j
@RestController
@RequestMapping("/newsviews")
public class NewsViewsController implements NewsViewsApi {

    @Autowired
    private INewsService newsService;

    @Autowired
    private INewsTypeService newsTypeService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserBrowsingHistoryService historyService;

    @Resource
    private RedisUtil<News> redisUtil;

    private static Map<String, List<HotNewsDTO>> hotNewsMap = new HashMap<>();

    private static Map<String, List<News>> newsMap = new HashMap<>();

    /**
     * newsId - 评论
     */
    private static Map<Integer, List<NewsCommentDTO>> newsCommentMap = new HashMap<>();

    private static final String HOT_NEWS_MAP = "hot_news_list";

    private static final String NEWS_LIST_LOCK = "news_list_lock";

    /**
     * redis 缓存的新闻列表
     */
    private static final String REDIS_NEWS_LIST = "newsList";

    @Override
    @GetMapping("/queryNewsDetails")
    public JsonResult<Object> getNewsDetail(@RequestBody NewsDetailsQuery newsDetailsQuery) {
        if(newsDetailsQuery.getNewsId() <= 0) {
            log.info("[INFO][新闻详情]新闻详情查看失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        News news = newsService.getNewsDetails(newsDetailsQuery.getUserId(), newsDetailsQuery.getNewsId());
        if(news == null) {
            log.info("[INFO][新闻详情]新闻详情查看失败!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "该新闻不存在！");
        }
        log.info("[INFO][新闻详情]新闻详情查看成功!");
        return JsonResult.success(news);
    }

    @Override
    @GetMapping("/getComment")
    public JsonResult<Object> getNewsComment(Integer newsId) {
        List<NewsCommentDTO> commentList = new ArrayList<>();
        if(newsCommentMap.get(newsId) != null) {
            log.info("[INFO][获取评论]获取评论成功!");
            return JsonResult.success(newsCommentMap.get(newsId));
        }
        // 尝试获取评论
        commentList = commentService.getNewsAndUserCommentList(newsId);
        if(commentList.size() > 0) newsCommentMap.put(newsId, commentList);
        log.info("[INFO][获取评论]获取评论成功!");
        return JsonResult.success(commentList);
    }

    @Override
    @GetMapping("/hotpic")
    public JsonResult<Object> getNewsPic() {
        if(hotNewsMap.isEmpty()) {
            log.info("[INFO][获取轮播图]缓存为空，获取数据!");
            this.getHotNewsList();
        }
        // 获取对应的热点新闻序列集合
        List<HotNewsDTO> hotNewsDTOList = hotNewsMap.get(HOT_NEWS_MAP);
        int len = 5;
        if(hotNewsDTOList.size() < 5) len = hotNewsDTOList.size();
        List<NewsPicDTO> newsPicDTOList = new ArrayList<>();
        for(int i = 0; i < len; i++) {
            // 排除没有图片的新闻
            if(hotNewsDTOList.get(i).getNews().getPicUrl() == null
                    || hotNewsDTOList.get(i).getNews().getPicUrl().equals("[]")) {
                --i;
                continue;
            }
            News n = hotNewsDTOList.get(i).getNews();
            NewsPicDTO newsPicDTO = new NewsPicDTO();
            newsPicDTO.setNewsId(n.getId());
            // 图片不能能为空
            String pic = n.getPicUrl();
            pic = pic.substring(1, pic.length() - 1);
            String[] picList = pic.split(",");
            newsPicDTO.setPicUrl(picList[0]);
            newsPicDTO.setTitle(n.getNewsTitle());
            newsPicDTOList.add(newsPicDTO);
        }
        return JsonResult.success(newsPicDTOList);
    }

    @Override
    @GetMapping("/queryNews")
    public JsonResult<Object> queryNewsList() {
        List<News> newsList = null;
        if(redisUtil.exists(REDIS_NEWS_LIST)) {
            System.out.println("读取缓存");
            newsList = redisUtil.lGet(REDIS_NEWS_LIST, 0, -1);
            return JsonResult.success(newsList);
        }
        try {
            log.info("[INFO][获取新闻列表]获取新闻列表!");
            boolean b = redisUtil.lock(NEWS_LIST_LOCK, 1);
            if (b) {
                newsList = newsService.getNewsList();
                redisUtil.lSet(REDIS_NEWS_LIST, newsList, 60);
                // return JsonResult.success(newsList);
            } else {
                log.info("获取锁错误{}", b);
                return null;
            }
        } catch (Exception e) {
            log.info("[INFO]获取锁异常{}", e.getMessage());
        } finally {
            //删除锁；
            System.out.println("删除 lock :" + redisUtil.deleteLock(NEWS_LIST_LOCK));
        }
        return JsonResult.success(newsList);
    }

    @Override
    @GetMapping("/getNewsList")
    public JsonResult<Object> getNewsList() {
        List<News> newsList = new ArrayList<>();
        if(hotNewsMap.containsKey(HOT_NEWS_MAP)) {
            newsList = newsMap.get(REDIS_NEWS_LIST);
        } else {
            newsList = newsService.getNewsList();
            if(newsList.isEmpty()) return JsonResult.error(ErrorCode.SERVER_ERROR, "热点新闻列表为空!");
            // 添加缓存
            newsMap.put(REDIS_NEWS_LIST, newsList);
        }
        if(Objects.isNull(newsList)) {
            log.error("新闻数据为空,获取失败");
        }
        return JsonResult.success(newsList);
    }

    /**
     * 获取热点新闻列表
     * @return
     */
    @Override
    @GetMapping("/hotnews")
    public JsonResult<Object> getHotNewsList() {
        List<HotNewsDTO> hotNewsDTOList = new ArrayList<>();
        if(hotNewsMap.containsKey(HOT_NEWS_MAP)) {
            hotNewsDTOList = hotNewsMap.get(HOT_NEWS_MAP);
        } else {
            hotNewsDTOList = newsService.hotNewsList();
            if(hotNewsDTOList.isEmpty()) return JsonResult.error(ErrorCode.SERVER_ERROR, "热点新闻列表为空!");
            // 热点排序
            Collections.sort(hotNewsDTOList);
            // 添加缓存
            hotNewsMap.put(HOT_NEWS_MAP, hotNewsDTOList.subList(0, 100));
        }
        if(Objects.isNull(hotNewsDTOList)) {
            log.error("热点数据为空,获取失败");
        }
        return JsonResult.success(hotNewsDTOList);
    }

    @Override
    public JsonResult<Object> getNewsListByKey(NewsQuery newsQuery) {
        List<News> newsList = newsService.getNewsList(newsQuery);
        if(newsList == null) {
            log.info("[INFO][搜索新闻]搜索新闻失败!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "该新闻不存在！");
        }
        //
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
        if(StringUtils.isEmpty(newsTypeQuery.getName())) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户不存在！");
        Page<News> publishNewsTypePage = newsTypeService.getNewTypePage(pageIndex, pageSize, newsTypeQuery);
        if(publishNewsTypePage == null) {
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "查询失败！");
        }
        log.info("[INFO][查询新闻列表]获取新闻列表成功!");
        // 分页数据转换
        PageResult<News> pageResult = PageResult.create(publishNewsTypePage);
        return JsonResult.success(pageResult);
    }

    @Override
    @PostMapping("/comment")
    public JsonResult<Object> commentNews(
            @Validated(value = {BaseDTO.Insert.class})
            @RequestBody CommentNewsDTORequest commentNewsDTORequest) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentNewsDTORequest, comment);
        boolean flag = commentService.saveComment(comment);
        if(!flag) {
            log.info("[INFO][新闻评论]新闻评论失败!");
            return JsonResult.error(ErrorCode.SERVER_ERROR, "评论失败!");
        }
        return JsonResult.success("评论成功");
    }

    @Override
    @PutMapping("/joke")
    public JsonResult<Object> joke(@RequestBody NewsDetailsQuery newsDetailsQuery) {
        boolean f = newsService.jokeNews(newsDetailsQuery);
        if(!f) return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻点赞失败");
        return JsonResult.success("新闻点赞成功!");
    }
}
