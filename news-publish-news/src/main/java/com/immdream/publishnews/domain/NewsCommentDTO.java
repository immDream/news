package com.immdream.publishnews.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.publishnews.domain
 *
 * @author immDream
 * @date 2023/06/07/22:50
 * @since 1.8
 */
@Data
public class NewsCommentDTO {
    private Integer userid;
    private String username;
    private Integer newsid;
    private String comments;
    private String userheadPortrait;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;
}
