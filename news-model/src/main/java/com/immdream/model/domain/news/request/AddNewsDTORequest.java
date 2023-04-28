package com.immdream.model.domain.news.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.news.request
 *
 * @author immDream
 * @date 2023/04/27/12:04
 * @since 1.8
 */
@Data
public class AddNewsDTORequest extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 6639365599787716586L;
    /**
     * 新闻标题
     */
    @NotEmpty(groups = {Insert.class}, message = "新闻标题不可为空！")
    private String newsTitle;

    /**
     * 新闻内容
     */
    @NotEmpty(groups = {Insert.class}, message = "新闻内容不可为空！")
    private String newsContent;

    /**
     * 作者
     */
    @NotEmpty(groups = {Insert.class}, message = "新闻作者不可为空！")
    private Integer author;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 新闻标题图片地址路径,命名方式为news_id + # + timestamp
     */
    private String newsImagePath;

    /**
     * 新闻原地址
     */
    private String newsLink;

    /**
     * 新闻模块 category_id
     */
    @NotEmpty(groups = {Insert.class}, message = "新闻模块不可为空！")
    private Integer newsModules;

    /**
     * 新闻标签，用分号分割
     */
    private String newsTags;
}
