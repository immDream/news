package com.immdream.usermanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.usermanager.domain
 *
 * @author immDream
 * @date 2023/06/07/20:33
 * @since 1.8
 */
@Data
public class HistoryNewsDTO {

    private String newsid;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;
}
