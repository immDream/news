package com.immdream.model.domain.user.query;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 查询用户发布的新闻
 * <p>
 * news com.immdream.model.domain.user.query
 *
 * @author immDream
 * @date 2023/04/24/22:57
 * @since 1.8
 */
@Data
public class UserPublishNewsQuery extends BaseDTO {
    private static final long serialVersionUID = -3054600064981835817L;
    /**
     * 用户 id
     */
    @NotEmpty(groups = {Select.class}, message = "用户id不可为空")
    Integer id;

    /**
     * 新闻的名字
     */
    String newsName;
}
