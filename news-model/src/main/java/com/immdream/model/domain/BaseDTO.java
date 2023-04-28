package com.immdream.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用来定义一个非查询的数据传输对象
 * <p>
 * news com.immdream.commons.domain
 *
 * @author immDream
 * @date 2023/04/16/21:31
 * @since 1.8
 */
@Data
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = -2032639531229309377L;

    /**
     * 插入的分组标识接口
     */
    public interface Insert {}

    /**
     * 修改的分组标识接口
     */
    public interface Update {}

    /**
     * 查询的分组标识接口
     */
    public interface Select {}
}
