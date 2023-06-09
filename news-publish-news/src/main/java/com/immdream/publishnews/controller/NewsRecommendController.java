package com.immdream.publishnews.controller;

import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.dto.HotNewsDTO;
import com.immdream.publishnews.service.INewsService;
import com.immdream.publishnews.service.NewsRecommendService;
import com.immdream.webapi.news.NewsRecommendApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.publishnews.controller
 *
 * @author immDream
 * @date 2023/06/08/23:37
 * @since 1.8
 */
@RestController
@Slf4j
@RequestMapping("/newsrecommend")
public class NewsRecommendController implements NewsRecommendApi {

    @Autowired
    private INewsService newsService;

    @Autowired
    private NewsRecommendService recommendService;

    /**
     * newsId - news    新闻详情
     */
    private static Map<Integer, News> newsMap = new HashMap<>();


    /**
     * 新闻推荐
     * @param userId 被推荐用户
     * @return
     */
    @Override
    @GetMapping("/recommendNews")
    public JsonResult<Object> newsRecommend(Integer userId) {
        if(userId == null || userId < 0 || userId == 100000) {
            log.error("非推荐拥护");
            JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "请求参数错误！");
        }
        List<News> newsList = recommendService.recommendNews(userId);
        if(newsList.size() < 50) {
            List<News> hotNews = newsService.hotNewsList().stream()
                    .map(HotNewsDTO::getNews).collect(Collectors.toList());
            for(int i = newsList.size(); i < 50; i++) {
                newsList.add(hotNews.get(i));
            }
        }
        Collections.shuffle(newsList);
        return JsonResult.success(newsList);
    }

}
