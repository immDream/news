package com.immdream.publishnews.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.news.dto.HotNewsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 返回置顶新闻列表
     * @return
     */
    List<News> selectNewsListTop();

    List<News> selectHotNewsList(@Param("time") Date time);
}
