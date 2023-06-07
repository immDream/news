package com.immdream.model.domain.user.vo;

import lombok.Data;
import lombok.ToString;

/**
 * UnimportantUser.
 * <p>
 * news com.immdream.model.domain.user.dto
 *
 * @author immDream
 * @date 2023/04/16/23:13
 * @since 1.8
 */
@Data
@ToString
public class UserVo {
    /**
     * 用户 id
     */
    private Integer id;

    /**
     * 用户账户,不超过16字符
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像，默认相对地址
     */
    private String image;

    /**
     * 用户描述
     */
    private String describe;

    private boolean isBan;

}
