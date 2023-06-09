package com.immdream.publishnews.service.impl;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.publishnews.domain.Feature;
import com.immdream.publishnews.mapper.UserBrowsingHistoryMapper;
import com.immdream.publishnews.service.IUserBrowsingHistoryService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-24
 */
@Service
public class UserBrowsingHistoryServiceImpl extends ServiceImpl<UserBrowsingHistoryMapper, UserBrowsingHistory> implements IUserBrowsingHistoryService {

    @Resource
    private UserBrowsingHistoryMapper historyMapper;

    @Override
    public Map<Integer, Integer> calFeature(Integer userId) {
        Map<Integer, Integer> feature = new HashMap<>();
        List<Feature> featureList = historyMapper.selectUserFeature(userId);
        for(Feature f : featureList) {
            feature.put(f.getType(), f.getFeature());
        }
        if(feature == null || feature.isEmpty()) {
            log.error("[ERROR][获取特征值]获取特征值失败，用户可能没有浏览数据！");
            feature = new HashMap<>();
        }
        // 初始化所有分类
        if(feature.size() != 10) {
            for (int i = 0; i < 10; i++) {
                feature.putIfAbsent(i, 0);
            }
        }
        return feature;
    }

    /**
     * 用户进一个月的历史新闻
     * @param userId
     * @return
     */
    @Override
    public List<News> userHistoryNews(Integer userId) {
        if(userId < 0) return null;
        return historyMapper.selectUserHistoryNews(userId);
    }

    @Override
    public List<Integer> getUserIds() {
        List<UserBrowsingHistory> userIds = new LambdaQueryChainWrapper<>(historyMapper)
                .select(UserBrowsingHistory::getUserId)
                .groupBy(UserBrowsingHistory::getUserId)
                .list();
        return userIds.stream()
                .map(UserBrowsingHistory::getUserId)
                .collect(Collectors.toList());
    }
}
