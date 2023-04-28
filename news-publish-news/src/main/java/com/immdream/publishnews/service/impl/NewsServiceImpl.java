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
import com.immdream.model.domain.news.query.NewsQuery;
import com.immdream.model.domain.news.request.AddNewsDTORequest;
import com.immdream.model.domain.news.request.DeleteNewsDTORequest;
import com.immdream.model.domain.user.User;
import com.immdream.publishnews.feign.UserClient;
import com.immdream.publishnews.mapper.NewsMapper;
import com.immdream.publishnews.service.INewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
    public boolean deleteNews(String id) {
        if(StringUtils.isEmpty(id)) return false;
        return new LambdaUpdateChainWrapper<>(newsMapper)
        .set(News::getIsDeleted, true)
        .eq(News::getId, id)
        .update();
    }

    @Override
    public boolean topNews(Integer id) {
        if(id <= 0) return false;
        return new LambdaUpdateChainWrapper<>(newsMapper)
                .set(News::getIsTop, true)
                .eq(News::getId, id)
                .update();
    }

    @Override
    public News getNewsDetails(String id) {
        if(StringUtils.isEmpty(id)) return null;
        return new LambdaQueryChainWrapper<>(newsMapper)
                .eq(News::getId, id).one();
    }

    @Override
    public List<News> getNewsList() {
        return new LambdaQueryChainWrapper<>(newsMapper).list();
    }

    @Override
    public List<News> getNewsList(NewsQuery newsQuery) {
        if(newsQuery == null) return null;
        String title = newsQuery.getNewsTitle();
        int authorId = newsQuery.getAuthor();
        if(!StringUtils.isEmpty(title)) {
            return new LambdaQueryChainWrapper<>(newsMapper)
                    .like(News::getNewsTitle, title)
                    .list();
        }
        if(authorId > 0) {
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
