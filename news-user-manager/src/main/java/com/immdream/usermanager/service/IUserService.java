package com.immdream.usermanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.RegisterUserDTORequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录
     * @param userDTO
     * @return
     */
    JsonResult login(UserDTO userDTO);

    /**
     * 注册用户
     * @param userDTO
     * @return
     */
    JsonResult register(UserDTO userDTO);

    /**
     * 通过构造条件查询用户
     * @param condition 用户查询条件
     * @return
     */
    List<User> selectUserListByCondition(User condition);

    /**
     * 根据查询条件查询指定用户分页信息的数据
     * @param pageIndex     查询的页码
     * @param pageSize      每页查询条数
     * @param userQuery     查询条件
     * @return              查询结果
     */
    List<User> listUsers(int pageIndex, int pageSize, UserQuery userQuery);

    /**
     * 保存新增用户
     * @param userDTO
     * @return
     */
    boolean saveUser(UserDTO userDTO);
}
