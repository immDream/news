package com.immdream.model.domain.news.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加新闻分类请求
 * <p>
 * news com.immdream.model.domain.news.request
 *
 * @author immDream
 * @date 2023/04/27/12:11
 * @since 1.8
 */
@Data
public class NewsTypeDTORequest implements Serializable {
    private static final long serialVersionUID = -582420899198941992L;

    /**
     * 新闻 id
     */
    private Integer id;

    /**
     * 新闻类型名称
     */
    private String name;

}
