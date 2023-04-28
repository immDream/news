package com.immdream.model.domain.news.request;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 删除新闻请求
 * <p>
 * news com.immdream.model.domain.news.request
 *
 * @author immDream
 * @date 2023/04/27/12:08
 * @since 1.8
 */
@Data
public class DeleteNewsDTORequest implements Serializable {
    private static final long serialVersionUID = -4246345154142644600L;
    /**
     * 新闻 id
     */
    @NotEmpty(groups = {BaseDTO.Insert.class})
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

