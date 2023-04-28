package com.immdream.model.domain.news.query;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.news
 *
 * @author immDream
 * @date 2023/04/27/12:15
 * @since 1.8
 */
@Data
public class NewsQuery {
    /**
     * 新闻 id
     */
    private String id;

    /**
     * 新闻标题
     */
    private String newsTitle;

    /**
     * 作者
     */
    private Integer author;
}
