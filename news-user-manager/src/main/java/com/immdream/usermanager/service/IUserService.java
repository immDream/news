package com.immdream.usermanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.query.UserPublishNewsQuery;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.FollowUserDTORequest;
import com.immdream.model.domain.user.request.LoginUserDTORequest;
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

    JsonResult loginByTelephone(LoginUserDTORequest userDTO);

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

    /**
     * 根据用户名删除用户
     * @param username
     * @return
     */
    boolean deleteUserByUsername(String username);

    /**
     * 修改当前拥用户的用户信息 - 需要用户类中含有 id
     * @param userDTO
     * @return
     */
    boolean updateUserInfoById(UserDTO userDTO);

    /**
     * 通过 id 修改手机号
     * @param id
     * @param telephone
     * @return
     */
    boolean updateTelephoneById(Integer id, String telephone);

    /**
     * 通过 id 更改邮箱
     * @param id
     * @param email
     * @return
     */
    boolean updateEmailById(Integer id, String email);

    /**
     * 查询当前用户的 历史浏览记录
     * @param id
     * @return
     */
    JsonResult listHistoryViewsById(Integer id);

    /**
     * 获取用户的 关注列表
     * @param pageIndex
     * @param pageSize
     * @param id
     * @return
     */
    Page<UserQuery> listAttentionUsers(int pageIndex, int pageSize, Integer id);

    /**
     * 查询 用户 发布的新闻
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<News> listPublishNews(Integer id, int pageIndex, int pageSize);

    /**
     * 根据关键词 查询 用户 发布的新闻
     * @param userPublishNewsQuery
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<News> listPublishNewsByNewsName(UserPublishNewsQuery userPublishNewsQuery, int pageIndex, int pageSize);

    /**
     * 根据 id 查询关注用户
     * @param followUserDTORequest
     * @return
     */
    UserQuery getFollowUserByAttentionUserId(FollowUserDTORequest followUserDTORequest);

    /**
     * 关注用户
     * @param followUserDTORequest
     * @return
     */
    boolean followUser(FollowUserDTORequest followUserDTORequest);

    /**
     * 关注用户
     * @param followUserDTORequest
     * @return
     */
    boolean unfollowUser(FollowUserDTORequest followUserDTORequest);

    /**
     * 关注用户
     * @param followUserDTORequest
     * @return
     */
    boolean particularUser(FollowUserDTORequest followUserDTORequest);
}
