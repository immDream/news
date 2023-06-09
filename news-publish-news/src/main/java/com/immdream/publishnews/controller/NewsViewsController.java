package com.immdream.publishnews.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.immdream.model.domain.user.User;
import com.immdream.publishnews.domain.NewsCommentDTO;
import com.immdream.publishnews.domain.NewsPicDTO;
import com.immdream.publishnews.feign.UserClient;
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
    private UserClient userClient;

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

    private static final String REDIS_NEWS_TYPE_LIST = "newsTypeList";

    @Override
    @GetMapping("/queryNewsDetails")
    public JsonResult<Object> getNewsDetail(Integer newsId, Integer userId) {
        if(newsId <= 0) {
            log.info("[INFO][新闻详情]新闻详情查看失败，请求参数为空!");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户请求参数为空！");
        }
        News news = newsService.getNewsDetails(userId, newsId);
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
        if(hotNewsMap.isEmpty()
                || hotNewsMap.get(HOT_NEWS_MAP) == null
                || hotNewsMap.get(HOT_NEWS_MAP).isEmpty()) {
            log.info("[INFO][获取轮播图]缓存为空，获取数据!");
            this.getHotNewsList();
        }
        // 获取对应的热点新闻序列集合
        List<HotNewsDTO> hotNewsDTOList = hotNewsMap.get(HOT_NEWS_MAP);
        log.info("[INFO][获取轮播图]数据存在{}条!", hotNewsDTOList.size());
        int len = 5;
        if(hotNewsDTOList.size() < 5) len = hotNewsDTOList.size();
        List<NewsPicDTO> newsPicDTOList = new LinkedList<>();
        for(int i = 0; i < len; i++) {
            // 排除没有图片的新闻
            if(hotNewsDTOList.get(i).getNews().getPicUrl() == null
                    || hotNewsDTOList.get(i).getNews().getPicUrl().equals("[]")) {
                hotNewsDTOList.remove(i);
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
        log.info("[INFO][获取轮播图]数据存在{}条!", newsPicDTOList.size());
        return JsonResult.success(newsPicDTOList);
    }


    /**
     * redis 缓存
     * @return
     */
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
        if(newsMap.containsKey(REDIS_NEWS_LIST)
                && newsMap.get(REDIS_NEWS_LIST) != null
                && !newsMap.get(REDIS_NEWS_LIST).isEmpty()) {
            newsList = newsMap.get(REDIS_NEWS_LIST);
        } else {
            newsList = newsService.getNewsList();
            if(newsList == null || newsList.isEmpty()) return JsonResult.error(ErrorCode.SERVER_ERROR, "热点新闻列表为空!");
            // 添加缓存
            newsMap.put(REDIS_NEWS_LIST, newsList);
        }
        if(Objects.isNull(newsList)) {
            log.error("新闻数据为空,获取失败");
        }
        return JsonResult.success(newsList);
    }

    /**
     * 获取某一新闻类型的新闻列表
     * @param type
     * @return
     */
    @Override
    @GetMapping("/typeNews")
    public JsonResult<Object> getTypeNewsList(Integer type) {
        List<News> newsList;
        if(newsMap.containsKey(REDIS_NEWS_TYPE_LIST + type)
                && newsMap.get(REDIS_NEWS_TYPE_LIST) != null
                && newsMap.get(REDIS_NEWS_TYPE_LIST + type).size() > 0) {
            newsList = newsMap.get(REDIS_NEWS_TYPE_LIST);
        } else {
            log.info("[INFO][获取类型新闻]新闻缓存为空，获取新闻，分类为:" + type);
            if (newsTypeService.getNewsType(type) == null)
                return JsonResult.error(ErrorCode.SERVER_ERROR, "该新闻分类不存在");
            else {
                newsList = newsService.getTypeNewsList(type);
                if (newsList == null || newsList.isEmpty()) {
                    return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻列表为空");
                }
                newsList.sort((o1, o2) -> o1.getCreateTime().after(o2.getCreateTime()) ? -1: 1);
                newsMap.put(REDIS_NEWS_TYPE_LIST + type, newsList);
                log.info("[INFO][获取类型新闻]成功获取新闻并添加到缓存中");
            }
        }
        return JsonResult.success(newsList);
    }

    /**
     * 获取热点新闻列表
     * @return
     */
    @Override
    @GetMapping("/hotnews")
    public synchronized JsonResult<Object> getHotNewsList() {
        List<HotNewsDTO> hotNewsDTOList = new ArrayList<>();
        if(hotNewsMap.containsKey(HOT_NEWS_MAP)
                && hotNewsMap.get(HOT_NEWS_MAP) != null
                && hotNewsMap.get(HOT_NEWS_MAP).size() > 0) {
            hotNewsDTOList = hotNewsMap.get(HOT_NEWS_MAP);
        } else {
            hotNewsDTOList = newsService.hotNewsList();
            if(hotNewsDTOList.isEmpty()) return JsonResult.error(ErrorCode.SERVER_ERROR, "热点新闻列表为空!");
            // 热点排序
            hotNewsDTOList.sort(null);
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
        NewsType newsTypes = newsTypeService.getNewsType(newsTypeQuery.getId());
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
        User user = JSON.parseObject(JSONObject.toJSONString(
                userClient.queryUserById(commentNewsDTORequest.getUserId()).getData())
                , User.class);
        if(user.getBan()) {
            log.info("[INFO][用户评论]禁止用户{}评论!", user.getId());
            return JsonResult.error(ErrorCode.SERVER_ERROR, "禁止评论");
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentNewsDTORequest, comment);
        System.out.println(comment);
        boolean flag = commentService.saveComment(comment);
        if(!flag) {
            log.info("[INFO][新闻评论]新闻评论失败!");
            return JsonResult.error(ErrorCode.SERVER_ERROR, "评论失败!");
        }
        newsCommentMap.clear();
        return JsonResult.success("评论成功");
    }

    @Override
    @PutMapping("/joke")
    public JsonResult<Object> joke(@RequestBody NewsDetailsQuery newsDetailsQuery) {
        System.out.println(newsDetailsQuery);
        boolean f = newsService.jokeNews(newsDetailsQuery);
        if(!f) return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻点赞失败");
        return JsonResult.success("操作成功!");
    }

    @Override
    @PutMapping("/collect")
    public JsonResult<Object> collect(@RequestBody NewsDetailsQuery newsDetailsQuery) {
        boolean f = newsService.collectNews(newsDetailsQuery);
        if(!f) return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻推荐失败");
        return JsonResult.success("操作成功!");
    }

    @Override
    @PutMapping("/unlike")
    public JsonResult<Object> unlike(@RequestBody NewsDetailsQuery newsDetailsQuery) {
        boolean f = newsService.unlikeNews(newsDetailsQuery);
        if(!f) return JsonResult.error(ErrorCode.SERVER_ERROR, "新闻不感兴趣失败");
        return JsonResult.success("操作成功!");
    }

    public static void clearHotNews() {
        hotNewsMap.clear();
        log.info("[INFO][清空热点新闻缓存]");
    }

    public static void clearNews() {
        newsMap.clear();
        log.info("[INFO][清空新闻缓存]");
    }

    public static void clearComment() {
        newsCommentMap.clear();
        log.info("[INFO][清空评论缓存]");
    }
}
