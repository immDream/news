package com.immdream.model.domain.user.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 关注用户请求
 * <p>
 * news com.immdream.model.domain.user.request
 *
 * @author immDream
 * @date 2023/04/26/23:39
 * @since 1.8
 */
@Data
public class FollowUserDTORequest implements Serializable {
    private static final long serialVersionUID = -4969083572921356420L;
    /**
     * 当前用户id
     */
    private Integer userId;

    /**
     * 被关注用户id
     */
    private Integer attentionUserId;

    /**
     * 特别关心
     */
    private boolean isParticular;
}
