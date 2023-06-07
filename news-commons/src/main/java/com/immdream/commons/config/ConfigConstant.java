package com.immdream.commons.config;

/**
 * 一些基础配置
 * <p>
 * news com.immdream.commons.util
 *
 * @author immDream
 * @date 2023/05/01/11:21
 * @since 1.8
 */
public interface ConfigConstant {

    /**
     * 根据文字生成图片，存放的基础路径，跟随着tomcat的变化而变化
     * 详见：　tomcat_home/conf/server.xml - Context
     */
    String FONT_IMAGE_BASE_PATH="/all/user/image/";

    /**
     * 推荐的新闻id存储绝对路径
     */
    String JSON_IDS_BASE_PATH="/all/news/image/";

    /**
     * 用户管理页面，每页加载的数量
     */
    Integer MAGAGER_USER_PAGE_NUM=8;

    /**
     * 兴趣管理页面，每页加载的数量
     */
    Integer MANAGER_TASTE_PAGE_NUM=10;

    /**
     * 新闻来源管理页面，每页加载的数量
     */
    Integer MANAGER_SOURCE_PAGE_NUM=10;

    /**
     * 点赞每页显示的数量
     */
    Integer MANAGER_LIKED_PAGE_NUM=10;

    /**
     * 评论每页显示数量
     */
    Integer MANAGER_COMMENT_PAGE_NUM=8;

    /**
     *　新闻页面显示数量
     */
    Integer MANAGER_NEWS_PAGE_NUM=8;


}
