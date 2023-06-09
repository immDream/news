package com.immdream.publishnews.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.dto.HotNewsDTO;
import com.immdream.model.domain.news.query.NewsDetailsQuery;
import com.immdream.model.domain.news.query.NewsQuery;
import com.immdream.model.domain.news.request.AddNewsDTORequest;
import com.immdream.model.domain.news.request.DeleteNewsDTORequest;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.model.domain.user.dto.HistoryDTO;
import com.immdream.publishnews.feign.UserClient;
import com.immdream.publishnews.mapper.NewsMapper;
import com.immdream.publishnews.service.INewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Slf4j
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    @Resource
    private NewsMapper newsMapper;

    @Resource
    private UserClient userClient;

    /**
     * 热点新闻 - 72小时内的 - 100条
     * @return
     */
    @Override
    public List<HotNewsDTO> hotNewsList() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 3);
        Date time = now.getTime();
        // 获取一百条热点新闻数据
        List<News> newsList = newsMapper.selectHotNewsList(time);
        List<HotNewsDTO> hotNewsList = new ArrayList<>();
        for(News n : newsList) {
            HotNewsDTO t = new HotNewsDTO();
            t.setNews(n);
            // 计算热度
            Date nd = n.getCreateTime();
            Date ndd = new Date();
            // 半时差
            int hotHour = (int)Duration.between(nd.toInstant(), ndd.toInstant()).getSeconds() / 1800;
            int hot = hotHour * (-10) + n.getVisitCount() + n.getJokeCount() + n.getCommentCount() * 2 + n.getCollectionCount() * 4;
            t.setHot(hot);
            hotNewsList.add(t);
        }
        return hotNewsList;
    }

    @Override
    public boolean addNews(AddNewsDTORequest addNewsDTORequest) {
        if(addNewsDTORequest == null) {
            log.error("[INFO][添加新闻]用户请求参数为空");
            return false;
        }
        News news = new News();
        BeanUtils.copyProperties(addNewsDTORequest, news);
        return newsMapper.insert(news) == 1;
    }

    @Override
    public boolean deleteNews(Integer id) {
        if(id<=0) return false;
        return new LambdaUpdateChainWrapper<>(newsMapper)
        .set(News::getIsDeleted, true)
        .eq(News::getId, id)
        .update();
    }

    @Override
    public boolean updateNewsCount(News news) {
        return newsMapper.updateById(news) == 1;
    }

    @Override
    public boolean jokeNews(NewsDetailsQuery newsDetailsQuery) {
        Integer newsId = newsDetailsQuery.getNewsId();
        Integer userId = newsDetailsQuery.getUserId();
        // System.out.println(newsId);
        News news = getNewsDetails(newsId);
        if(Objects.nonNull(news)) {
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setUserId(userId);
            historyDTO.setNewsId(newsId);
            // 获取历史记录
            JsonResult<Object> jsonResult = userClient.getOneNewsHistoryRecord(historyDTO);
            // 历史记录存在，更新当前数据
            if(Objects.nonNull(jsonResult)) {
                UserBrowsingHistory history = JSON.parseObject(JSONObject.toJSONString(jsonResult.getData()), UserBrowsingHistory.class);
                history.setJoke(!history.getJoke());
                history.setUninterested(false);
                BeanUtils.copyProperties(history, historyDTO);
            }
            boolean isJoke = historyDTO.getJoke();
            jsonResult = userClient.historyRecord(historyDTO);
            if (jsonResult.getErrorCode() == null) {
                log.info("用户浏览记录插入成功");
                if(isJoke) {
                    log.info("用户{}点赞新闻{}", historyDTO.getUserId(), historyDTO.getNewsId());
                    news.setJokeCount(news.getJokeCount() + 1);
                } else {
                    log.info("用户{}取消点赞新闻{}", historyDTO.getUserId(), historyDTO.getNewsId());
                    news.setJokeCount(news.getJokeCount() - 1);
                }
                // 更新新闻操作
                boolean f = this.updateNewsCount(news);
                if (!f) {
                    log.info("用户操作失败");
                    return false;
                } else {
                    log.info("用户操作成功");
                    return true;
                }
            } else {
                log.info("用户浏览记录插入失败: {}", jsonResult.getErrorMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * 不感兴趣
     * @param newsDetailsQuery
     * @return
     */
    @Override
    public boolean unlikeNews(NewsDetailsQuery newsDetailsQuery) {
        Integer newsId = newsDetailsQuery.getNewsId();
        Integer userId = newsDetailsQuery.getUserId();
        News news = getNewsDetails(newsId);
        if(Objects.nonNull(news)) {
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setUserId(userId);
            historyDTO.setNewsId(newsId);
            // 获取历史记录
            JsonResult<Object> jsonResult = userClient.getOneNewsHistoryRecord(historyDTO);
            // 历史记录存在，更新当前数据
            if(Objects.nonNull(jsonResult)) {
                UserBrowsingHistory history = JSON.parseObject(JSONObject.toJSONString(jsonResult.getData()), UserBrowsingHistory.class);
                history.setJoke(false);
                history.setUninterested(!history.getUninterested());
                BeanUtils.copyProperties(history, historyDTO);
            }
            boolean isUnlike = historyDTO.getUninterested();
            jsonResult = userClient.historyRecord(historyDTO);
            if (jsonResult.getErrorCode() == null) {
                log.info("用户浏览记录插入成功");
                if(isUnlike) {
                    log.info("用户{}不感兴趣，新闻{}", historyDTO.getUserId(), historyDTO.getNewsId());
                    news.setJokeCount(news.getJokeCount() - 1);
                } else {
                    log.info("用户{}取消不感兴趣，新闻{}", historyDTO.getUserId(), historyDTO.getNewsId());
                    news.setJokeCount(news.getJokeCount() + 1);
                }
                // 更新新闻操作
                boolean f = this.updateNewsCount(news);
                if (!f) {
                    log.info("用户操作失败");
                    return false;
                } else {
                    log.info("用户操作成功");
                    return true;
                }
            } else {
                log.info("用户浏览记录插入失败: {}", jsonResult.getErrorMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * 新闻收藏/取消收藏
     * @param newsDetailsQuery
     * @return
     */
    @Override
    public boolean collectNews(NewsDetailsQuery newsDetailsQuery) {
        Integer newsId = newsDetailsQuery.getNewsId();
        Integer userId = newsDetailsQuery.getUserId();
        // System.out.println(newsId);
        News news = getNewsDetails(newsId);
        if(Objects.nonNull(news)) {
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setUserId(userId);
            historyDTO.setNewsId(newsId);
            // 获取历史记录
            JsonResult<Object> jsonResult = userClient.getOneNewsHistoryRecord(historyDTO);
            // 历史记录存在，更新当前数据
            if(Objects.nonNull(jsonResult)) {
                UserBrowsingHistory history = JSON.parseObject(JSONObject.toJSONString(jsonResult.getData()), UserBrowsingHistory.class);
                history.setCollect(!history.getCollect());
                BeanUtils.copyProperties(history, historyDTO);
            }
            boolean isCollect = historyDTO.getCollect();
            jsonResult = userClient.historyRecord(historyDTO);
            if (jsonResult.getErrorCode() == null) {
                log.info("用户浏览记录插入成功");
                if(isCollect) {
                    log.info("用户{}收藏新闻{}", historyDTO.getUserId(), historyDTO.getNewsId());
                    news.setJokeCount(news.getCollectionCount() + 1);
                } else {
                    log.info("用户{}取消收藏新闻{}", historyDTO.getUserId(), historyDTO.getNewsId());
                    news.setJokeCount(news.getCollectionCount() - 1);
                }
                // 更新新闻操作
                boolean f = this.updateNewsCount(news);
                if (!f) {
                    log.info("用户操作失败");
                    return false;
                } else {
                    log.info("用户操作成功");
                    return true;
                }
            } else {
                log.info("用户浏览记录插入失败: {}", jsonResult.getErrorMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean topNews(Integer id, boolean isUp) {
        if(id <= 0) return false;
        return new LambdaUpdateChainWrapper<>(newsMapper)
                .set(News::getIsTop, isUp)
                .eq(News::getId, id)
                .update();
    }

    @Override
    public News getNewsDetails(Integer userId, Integer id) {
        if(id <= 0) return null;
        News news = new LambdaQueryChainWrapper<>(newsMapper)
                        .eq(News::getId, id).one();
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setUserId(userId);
        historyDTO.setNewsId(id);
        UserBrowsingHistory history = JSON.parseObject(JSONObject.toJSONString(
                userClient.getOneNewsHistoryRecord(historyDTO).getData()),
                UserBrowsingHistory.class);
        // if(history == null) userClient.historyRecord(historyDTO);
        if(Objects.nonNull(news)) {
            if(history != null) BeanUtils.copyProperties(history, historyDTO);
            JsonResult jsonResult = userClient.historyRecord(historyDTO);
            if (jsonResult.getErrorCode() == null) {
                log.info("用户浏览记录插入成功");
                news.setVisitCount(news.getVisitCount()+1);
                boolean c = this.updateNewsCount(news);
                if(c) {
                    log.info("新闻浏览量更新");
                } else {
                    log.info("新闻浏览量更新失败");
                }
            } else {
                log.info("用户浏览记录插入失败: {}", jsonResult.getErrorMessage());
            }
        }
        return news;
    }

    @Override
    public News getNewsDetails(Integer id) {
        if(id <= 0) return null;
        return new LambdaQueryChainWrapper<>(newsMapper)
                .eq(News::getId, id)
                .one();
    }

    @Override
    public List<News> getNewsList() {
        // return new LambdaQueryChainWrapper<>(newsMapper)
        //         .eq(News::getIsDeleted, false)
        //         .list();
        return newsMapper.selectNewsListTop();
    }

    @Override
    public List<News> getTypeNewsList(Integer type) {
        return new LambdaQueryChainWrapper<>(newsMapper)
                .eq(News::getNewsModules, type)
                .list();
    }

    @Override
    public List<News> getNewsList(NewsQuery newsQuery) {
        if(newsQuery == null) return null;
        String title = newsQuery.getNewsTitle();
        Integer authorId = newsQuery.getAuthor();
        if(!StringUtils.isEmpty(title)) {
            System.out.println("搜索:" + title);
            return new LambdaQueryChainWrapper<>(newsMapper)
                    .like(News::getNewsTitle, title)
                    .list();
        }
        if(authorId != null && authorId > 0) {
            log.info("[INFO][查询新闻]尝试调用远程服务获取用户信息");
            // 调用远程服务，获取用户
            User user;
            JsonResult res = userClient.queryUserById(authorId);
            // res.getData()是LinkedHashMap类型
            if(Objects.isNull(res)) {
                log.error("[ERROR][查询新闻]Feign远程调用获取用户失败!");
                return null;
            }
            log.info("[INFO][查询新闻]返回参数: {}", res.toString());
            user =  JSON.parseObject(JSONObject.toJSONString(res.getData()), User.class);
            log.info("[INFO][查询新闻]获取用户{}", user.toString());
            if(user == null) {
                log.info("[INFO][查询新闻]获取用户失败");
                return null;
            }
            return new LambdaQueryChainWrapper<>(newsMapper)
                    .like(News::getAuthor, authorId)
                    .list();
        }
        return null;
    }

    @Override
    public Page<News> getNewsPage(int pageIndex, int pageSize, NewsQuery newsQuery) {
        if (newsQuery == null) return null;
        String title = newsQuery.getNewsTitle();
        int author = newsQuery.getAuthor();
        // 开启分页
        Page<News> page = PageHelper.startPage(pageIndex, pageSize);
        // 根据标题模糊查询
        if(!StringUtils.isEmpty(title)) {
            new LambdaQueryChainWrapper<>(newsMapper)
                    .like(News::getNewsTitle, title).list();
            return page;
        }
        // 根据idchaxun
        if(author > 0) {
            new LambdaQueryChainWrapper<>(newsMapper)
                    .eq(News::getAuthor, author).list();
            return page;
        }
        // log.info("[INFO][查询新闻列表]用户不存在！");
        return null;
    }
}
