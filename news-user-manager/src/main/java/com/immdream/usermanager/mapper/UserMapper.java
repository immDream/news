package com.immdream.usermanager.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.vo.UserVo;
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

    /**
     * 查询 对应用户的关注列表
     * @param id
     * @return
     */
    List<UserQuery> listAttentionUser(Integer id);

    /**
     * 通过 id 获取用户视图
     * @param id
     * @return
     */
    UserQuery getUserVoById(Integer id);
}
