package com.immdream.model.domain.news.query;


import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.news.query
 *
 * @author immDream
 * @date 2023/04/27/12:17
 * @since 1.8
 */
@Data
public class NewsTypeQuery {
    private Integer id;

    /**
     * 新闻类型名称
     */
    private String name;

}
