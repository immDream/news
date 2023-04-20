package com.immdream.usermanager.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.immdream.model.domain.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据条件查询用户
     * @param condition 用户名 与 手机号 与 邮箱
     * @return
     */
    List<User> selectUserListByCondition(User condition);
}
