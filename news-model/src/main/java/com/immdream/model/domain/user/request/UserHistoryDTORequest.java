package com.immdream.model.domain.user.request;

import com.immdream.model.domain.user.UserBrowsingHistory;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户历史记录查询请求
 * <p>
 * news com.immdream.model.domain.user.request
 *
 * @author immDream
 * @date 2023/04/24/22:37
 * @since 1.8
 */
@Data
@ToString
public class UserHistoryDTORequest implements Serializable {
    private static final long serialVersionUID = 1836874237097961336L;

    /**
     * 用户 id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 开始时间 - 最小时间
     */
    private Date startTime;

    /**
     * 结束时间 - 最大时间
     */
    private Date endTime;

    /**
     * 历史记录
     */
    List<UserBrowsingHistory> histories;
}
