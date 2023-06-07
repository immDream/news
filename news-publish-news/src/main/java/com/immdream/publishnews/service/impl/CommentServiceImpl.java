package com.immdream.publishnews.service.impl;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.news.Comment;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.model.domain.user.dto.HistoryDTO;
import com.immdream.publishnews.domain.NewsCommentDTO;
import com.immdream.publishnews.feign.UserClient;
import com.immdream.publishnews.mapper.CommentMapper;
import com.immdream.publishnews.service.ICommentService;
import com.immdream.publishnews.service.INewsService;
import lombok.extern.slf4j.Slf4j;
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
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private INewsService newsService;

    @Resource
    private UserClient userClient;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public boolean saveComment(Comment comment) {
        boolean finished = this.save(comment);
        if(finished) {
            // 更新新闻字段记录
            News news = newsService.getNewsDetails(comment.getNewsId());
            news.setCommentCount(news.getCommentCount() + 1);
            boolean a = newsService.updateNewsCount(news);
            if(a) {
                HistoryDTO historyDTO = new HistoryDTO();
                historyDTO.setUserId(comment.getUserId());
                historyDTO.setNewsId(comment.getNewsId());
                // 获取历史记录
                JsonResult<Object> jsonResult = userClient.getOneNewsHistoryRecord(historyDTO);
                // 历史记录存在，更新当前数据
                if(Objects.nonNull(jsonResult)) {
                    UserBrowsingHistory history = (UserBrowsingHistory) jsonResult.getData();
                    history.setComment(true);
                    BeanUtils.copyProperties(history, historyDTO);
                }
                jsonResult = userClient.historyRecord(historyDTO);
                if (jsonResult.getErrorCode() == null) {
                    log.info("用户浏览记录插入成功");
                    news.setJokeCount(news.getJokeCount() + 1);
                    boolean f = newsService.updateNewsCount(news);
                    if (!f) {
                        log.info("用户评论失败");
                        return false;
                    } else {
                        log.info("用户评论成功");
                        return true;
                    }
                } else {
                    log.info("用户浏览记录插入失败: {}", jsonResult.getErrorMessage());
                }
            } else {
                log.info("新闻评论失败");
                this.removeById(comment.getId());
            }
        } else {
            log.info("新闻评论失败");
        }
        return false;
    }

    @Override
    public List<Comment> getCommentList() {
        return new LambdaQueryChainWrapper<Comment>(commentMapper)
                .orderBy(true, false, Comment::getCreateTime)
                .list();
    }

    /**
     * 传入新闻 id，获取对应新闻的评论
     * @param id
     * @return
     */
    @Override
    public List<Comment> getNewsCommentList(Integer id) {
        return new LambdaQueryChainWrapper<Comment>(commentMapper)
                .eq(Comment::getNewsId, id)
                .orderBy(true, false, Comment::getCreateTime)
                .list();
    }

    @Override
    public List<NewsCommentDTO> getNewsAndUserCommentList(Integer id) {
        return commentMapper.getNewsCommentList(id);
    }

    @Override
    public boolean banComment(Integer id) {
        return this.update()
                .set("is_deleted", true)
                .eq("id", id).update();
    }

    @Override
    public boolean unBanComment(Integer id) {
        return this.update()
                .set("is_deleted", false)
                .eq("id", id).update();
    }
}
