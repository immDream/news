package com.immdream.model.domain.user.query;

import com.immdream.model.domain.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 定义一个用来接收偏僻查询请求参数的类型
 * <p>
 * news com.immdream.model.domain.user.query
 *
 * @author immDream
 * @date 2023/04/16/22:22
 * @since 1.8
 */
@Data
public class UserQuery implements Serializable {

    private static final long serialVersionUID = 7442020310912479777L;

    @NotEmpty(groups = {BaseDTO.Select.class}, message = "用户id不可为空")
    private Integer id;

    /**
     * 查询用户名称
     */
    @NotEmpty(groups = {BaseDTO.Select.class}, message = "用户名不能为空")
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

}
