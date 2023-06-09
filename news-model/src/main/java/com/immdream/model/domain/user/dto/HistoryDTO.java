package com.immdream.model.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.model.domain.user.dto
 *
 * @author immDream
 * @date 2023/05/29/20:46
 * @since 1.8
 */
@Data
public class HistoryDTO implements Serializable {

    private static final long serialVersionUID = -986588014969424122L;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 新闻id
     */
    private Integer newsId;
    private Boolean deleted = false;
    private Boolean joke = false;
    private Boolean uninterested = false;
    private Boolean comment = false;
    private Boolean collect = false;

    /**
     * 浏览时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 数据更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
