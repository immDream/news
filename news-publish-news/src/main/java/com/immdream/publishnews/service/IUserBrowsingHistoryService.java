package com.immdream.publishnews.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.UserBrowsingHistory;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-24
 */
public interface IUserBrowsingHistoryService extends IService<UserBrowsingHistory> {


    /**
     * 推荐算法 - 计算用户特征值
     * @return
     */
    public Map<Integer, Integer> calFeature(Integer userId);

    List<News> userHistoryNews(Integer userId);

    List<Integer> getUserIds();

}
