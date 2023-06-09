package com.immdream.publishnews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.publishnews.domain.UserFeature;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 新闻推荐服务类
 * <p>
 * news com.immdream.publishnews.service
 *
 * @author immDream
 * @date 2023/06/08/23:59
 * @since 1.8
 */
@Slf4j
@Service
public class NewsRecommendService {

    @Autowired
    private INewsService newsService;

    @Autowired
    private INewsTypeService newsTypeService;

    @Autowired
    private IUserBrowsingHistoryService historyService;

    /**
     * 特征值缓存
     */
    @Autowired
    private static Map<Integer, UserFeature> featureMap = new HashMap();

    /**
     * 用户相似度缓存 userId ---- List<Pair<相似用户，相似度>>
     */
    @Autowired
    private static Map<Integer, Map<Integer, Double>> similarMap = new HashMap();

    public static void clearCache() {
        log.info("[INFO][清除缓存]清除用户相似度缓存成功！");
        similarMap.clear();
    }

    /**
     * 对用户单独 进行新闻推荐
     * @param userId
     * @return
     */
    public List<News> recommendNews(Integer userId) {
        // 向用户推荐新闻 - 获取到该用户浏览过的新闻集合 - 不进行推荐(一个月以内)
        Set<Integer> newsIdsSet = historyService.list().stream()
                .filter(o -> o.getUserId().equals(userId))
                .map(UserBrowsingHistory::getNewsId).collect(Collectors.toSet());
        // 获取当前所有有浏览记录的用户的ids
        List<Integer> userIds = historyService.getUserIds();
        // 计算所有用户的特征值
        featureMap = calAllUserFeature(userIds);
        // 获取和用户相似的用户集合
        calUserSimilar(userId);
        // 对相似度用户集合进行排序
        List<Pair<Integer, Double>> similarList = similarMap.get(userId).entrySet()
                .stream().map(o -> new Pair<>(o.getKey(), o.getValue()))
                .sorted(new Comparator<Pair<Integer, Double>>() {
                    @Override
                    public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
                        return o1.getValue() > o2.getValue() ? 1
                                : o1.getValue().equals(o2.getValue()) ? 0 : -1;
                    }
                }).collect(Collectors.toList());

        log.info("[INFO][相似度序列]用户{}的相似度排序:{}", userId,
                Arrays.toString(similarList.toArray()));
        List<News> simNewsList = recommendNews(similarList).stream()
                .filter(o -> !newsIdsSet.contains(o.getId()))
                .collect(Collectors.toList());
        Collections.shuffle(similarList);
        // 从相似集合中，获取相似用户浏览过但是该用户没有浏览的新闻集合(一个月以内)，打乱顺序进行推荐
        return simNewsList;
    }

    /**
     * 推荐一个月以内的用户阅读的新闻，新闻时效也不超过一个月
     * @param similarList
     * @return
     */
    private List<News> recommendNews(List<Pair<Integer, Double>> similarList) {
        int i = 0;
        List<News> recommendNewsList = new LinkedList<>();
        for(Pair<Integer, Double> u : similarList) {
            if(i > 1000) break;
            // 获取u的最近阅读
            List<News> uNews = historyService.userHistoryNews(u.getKey());
            if(uNews.size() > 50) recommendNewsList.addAll(uNews.subList(0, 50));
            else recommendNewsList.addAll(uNews);
            i += uNews.size();
        }
        return recommendNewsList;
    }



    /**
     * 计算所有用户的特征值
     * @param userIds
     * @return
     */
    private Map<Integer, UserFeature> calAllUserFeature(List<Integer> userIds) {
        log.info("[INFO][用户特征值计算] 用户集合: {}", userIds.toArray());
        // 对所有用户进行特征缓存
        for(Integer userId : userIds) {
            if(userId == 100000) {
                log.info("[INFO][用户特征值计算] 不计算游客！");
                continue;
            }
            UserFeature userFeature = calUserFeature(userId);
            // 缓存用户特征值
            featureMap.put(userId, userFeature);
        }
        log.info("[INFO][用户特征值计算] 特征值缓存: {}", featureMap);
        return featureMap;
    }


    /**
     * 计算用户的特征值
     * @return
     */
    @Nullable
    private UserFeature calUserFeature(Integer userId) {
        if(userId == 100000) {
            log.info("[INFO][计算特征值] 不计算游客！");
            return null;
        }
        Map<Integer, Integer> feature = historyService.calFeature(userId);
        UserFeature userFeature = new UserFeature();
        userFeature.setUserId(userId);
        userFeature.setNewsTypeFeature(feature);
        log.info("[INFO][计算特征值] 用户特征值为{}", userFeature);
        return userFeature;
    }

    /**
     *  计算用户之间的相似度
     * @param userId
     */
    private void calUserSimilar(Integer userId) {
        // 如果缓存中存在当前用户的相似度信息
        if(similarMap.containsKey(userId)) return;
        // 当前用户的特征值
        // UserFeature userFeature = featureMap.get(userId);
        log.info("[INFO][计算相似度]开始计算用户{}与其他用户的相似度", userId);
        // 计算  没有与 user 计算过相似度的用户
        featureMap.entrySet().stream().filter(o -> !similarMap.containsKey(o.getKey()))
                .forEach(u -> {
                    // 计算用户之间的相似度，并存储到缓存中
                    calUserSimilar(userId, u.getKey());
                });
        log.info("[INFO][计算相似度] 计算相似度完毕!");

    }

    /**
     * 计算两个用户之间的相似度
     * @param userId
     * @param userId2
     * @return
     */
    private void calUserSimilar(Integer userId, Integer userId2) {
        if(userId.equals(userId2) || (similarMap.get(userId) != null
                && similarMap.get(userId).containsKey(userId2))) return ;
        Map<Integer, Integer> feature1 = featureMap.get(userId).getNewsTypeFeature();
        Map<Integer, Integer> feature2 = featureMap.get(userId2).getNewsTypeFeature();
        double similar = 0;
        for(int i = 0; i < 10; i++) {
            similar += Math.pow(feature1.get(i) - feature2.get(i), 2);
        }
        similar = Math.sqrt(similar);
        log.info("[INFO][计算用户相似度]用户：{}与用户：{}之间相似度为：{}", userId, userId2, similar);
        // user1 - user2的相似度
        Map<Integer, Double> user1Map = similarMap.getOrDefault(userId, new HashMap<>());
        user1Map.put(userId2, similar);
        // user2 - 绑定和user1的相似度
        Map<Integer, Double> user2Map = similarMap.getOrDefault(userId2, new HashMap<>());
        user2Map.put(userId, similar);

        similarMap.put(userId,user1Map);
        similarMap.put(userId2,user2Map);

        // // user1 对其他用户的相似度集合
        // List<Pair<Integer, Double>> userList1 = similarMap.getOrDefault(userId, new LinkedList<>());
        // userList1.add(userSimilarPair1);
        //
        // // user2 对其他用户的相似度集合
        // List<Pair<Integer, Double>> userList2 = similarMap.getOrDefault(userId2, new LinkedList<>());
        // userList2.add(userSimilarPair2);

        // 存储到相似度缓存 - 绑定关系
        // similarMap.put(userId, userList1);
        // similarMap.put(userId2, userList2);
    }

}