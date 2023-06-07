package com.immdream.publishnews.domain;

import lombok.Data;

/**
 * 新闻轮播图
 * <p>
 * news com.immdream.publishnews.domain
 *
 * @author immDream
 * @date 2023/06/07/18:44
 * @since 1.8
 */
@Data
public class NewsPicDTO {
    private Integer newsId;

    private String picUrl;

    private String title;
}
