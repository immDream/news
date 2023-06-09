package com.immdream.publishnews.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.publishnews.domain.Feature;
import javafx.util.Pair;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author immDream
 * @since 2023-04-24
 */
public interface UserBrowsingHistoryMapper extends BaseMapper<UserBrowsingHistory> {

    List<Feature> selectUserFeature(@Param("userId") Integer userId);

    List<News> selectUserHistoryNews(@Param("userId") Integer userId);
}
