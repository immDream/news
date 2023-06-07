package com.immdream.usermanager.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.news.News;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.UserAttention;
import com.immdream.model.domain.user.UserBrowsingHistory;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.query.UserPublishNewsQuery;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.FollowUserDTORequest;
import com.immdream.model.domain.user.request.LoginUserDTORequest;
import com.immdream.model.domain.user.vo.UserVo;
import com.immdream.usermanager.domain.HistoryNewsDTO;
import com.immdream.usermanager.mapper.NewsMapper;
import com.immdream.usermanager.mapper.UserAttentionMapper;
import com.immdream.usermanager.mapper.UserBrowsingHistoryMapper;
import com.immdream.usermanager.mapper.UserMapper;
import com.immdream.usermanager.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserBrowsingHistoryMapper userBrowsingHistoryMapper;

    @Resource
    private NewsMapper newsMapper;

    @Resource
    private UserAttentionMapper userAttentionMapper;

    public JsonResult login(UserDTO loginUser) {
        User user = getOneByUsername(loginUser.getUsername());
        UserDTO userDTO = new UserDTO();
        if(user == null) return null;
        BeanUtils.copyProperties(user, userDTO);
        // System.out.println(userDTO.toString());
        if(userDTO.getUsername().equals(loginUser.getUsername())
                && userDTO.getPassword().equals(loginUser.getPassword())) {
            return JsonResult.success("用户登陆成功!", userDTO);
        }
        return JsonResult.error(ErrorCode.LOGIN_FAILED, "用户名或密码不正确");
    }

    @Override
    public JsonResult loginByTelephone(LoginUserDTORequest loginUser) {
        User user = getOneByTelephone(loginUser.getTelephone());
        UserDTO userDTO = new UserDTO();
        if(user == null) return null;
        BeanUtils.copyProperties(user, userDTO);
        // System.out.println(userDTO.toString());
        if(userDTO.getTelephone().equals(loginUser.getTelephone())
                && userDTO.getPassword().equals(loginUser.getPassword())) {
            return JsonResult.success("用户登陆成功!", userDTO);
        }
        return JsonResult.error(ErrorCode.LOGIN_FAILED, "用户名或密码不正确");
    }

    @Override
    public JsonResult register(UserDTO userDTO) {
        User condition = new User();
        BeanUtils.copyProperties(userDTO, condition);
        // 查询是否存在对应用户已经注册
        List<User> loginUsers = selectUserListByCondition(condition);
        if(loginUsers == null || loginUsers.size() < 1) {    // 没有注册过
            // 注册用户 - 保存入数据库
            if(this.saveUser(userDTO)) return JsonResult.success("用户注册成功！", userDTO);
            return JsonResult.error(ErrorCode.REGISTER_FAILED, "用户注册失败!");
        }
        // 注册过，提供反馈信息
        for (User u : loginUsers) {
            // 只校验 三种信息
            if(u.getUsername().equals(userDTO.getUsername())) {
                return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户名重复!");
            } else if (u.getTelephone().equals(userDTO.getTelephone())) {
                return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "该手机号已绑定!");
            } else {
                return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "该邮箱已绑定");
            }
        }
        return JsonResult.error(ErrorCode.REGISTER_FAILED, "用户注册失败!");
    }

    /**
     * 根据条件查询 - 用户名 和 手机号 和 邮箱
     * @param condition 用户查询条件
     * @return
     */
    @Override
    public List<User> selectUserListByCondition(User condition) {
        return userMapper.selectUserListByCondition(condition);
    }

    /**
     * 分页查询用户数据
     * @param pageIndex     查询的页码
     * @param pageSize      每页查询条数
     * @param userQuery     查询条件
     * @return
     */
    @Override
    public Page<User> listUsers(int pageIndex, int pageSize, UserQuery userQuery) {
        // 开启分页
        Page<User> page = PageHelper.startPage(pageIndex, pageSize);
        List<User> userList;
        if(userQuery == null) {
            log.info("[INFO][用户列表]查询条件为空则全部查询");
            userList = list();
        } else {
            log.info("[INFO][用户列表]按条件查询用户");
            userList = list();
        }
        if(Objects.isNull(userList)) {
            log.info("[INFO][用户列表]没有查询到用户！");
        } else {
            log.info("[INFO][用户列表]查询用户列表成功！");
        }
        return page;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setCreateTime(new Date());
        return userMapper.insert(user) == 1;
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        // User user = getOneByUsername(username);
        // if(Objects.isNull(user))
        // 直接删除用户
        if(StringUtils.isEmpty(username)) return false;
        log.info("[INFO][用户删除业务] 正在删除用户名为{}的用户！", username);
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return this.removeByMap(params);
    }

    @Override
    public boolean updateUserInfoById(UserDTO userDTO) {
        if(Objects.isNull(userDTO)) return false;
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        // if(StringUtils.isEmpty(user.getTelephone())) {
        //     return false;
        // }
        return new LambdaUpdateChainWrapper<>(userMapper)
                .set(User::getNickname, user.getNickname())
                .set(User::getDescribe, user.getDescribe())
                .eq(User::getId, user.getId()).update();
    }

    @Override
    public boolean updateTelephoneById(Integer id, String telephone) {
        System.out.println(telephone);
        // 需要先检验 telephone 合法性
        return new LambdaUpdateChainWrapper<>(userMapper)
                .set(User::getTelephone, telephone)
                .eq(User::getId, id).update();
    }

    @Override
    public boolean updateEmailById(Integer id, String email) {
        return new LambdaUpdateChainWrapper<>(userMapper)
                .set(User::getEmail, email)
                .eq(User::getId, id).update();
    }

    @Override
    public List<HistoryNewsDTO> listHistoryById(Integer id) {
        List<HistoryNewsDTO> historyNewsDTOList = userBrowsingHistoryMapper.getHistory(id);
        log.info("[INFO][浏览记录]用户浏览记录查询成功!");
        return historyNewsDTOList;
    }


    @Override
    public JsonResult listHistoryViewsById(Integer id) {
        if(id < 0) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户id错误!");
        List<UserBrowsingHistory> histories = new LambdaQueryChainWrapper<>(userBrowsingHistoryMapper)
                .eq(UserBrowsingHistory::getUserId, id).list();
        log.info("[INFO][浏览记录]用户浏览记录查询成功!");
        return JsonResult.success("查询成功！", histories);
    }

    @Override
    public Page<UserQuery> listAttentionUsers(int pageIndex, int pageSize, Integer id) {
        if(id < 0) {
            log.info("[INFO][关注列表]用户id错误");
            return null;
        }
        // 开启分页
        Page<UserQuery> page = PageHelper.startPage(pageIndex, pageSize);
        List<UserQuery> userQueries = listAttentionUsers(id);
        if(Objects.isNull(userQueries)) {
            log.info("[INFO][关注列表]用户:{}, 没有查询到关注列表！", id);
        } else {
            log.info("[INFO][关注列表]用户:{}, 查询关注列表成功！", id);
        }
        return page;
    }

    @Override
    public Page<News> listPublishNews(Integer id, int pageIndex, int pageSize) {
        if(id < 0) {
            log.info("[INFO][新闻发布列表]用户id错误");
            return null;
        }
        // 开启分页
        Page<News> page = PageHelper.startPage(pageIndex, pageSize);
        List<News> news = new LambdaQueryChainWrapper<>(newsMapper)
                .eq(News::getAuthor, id).eq(News::getIsDeleted, false).list();
        if(Objects.isNull(news)) {
            log.info("[INFO][新闻发布列表]用户:{}, 没有查询到新闻发布列表！", id);
        } else {
            log.info("[INFO][新闻发布列表]用户:{}, 查询新闻发布列表成功！", id);
        }
        return page;
    }

    @Override
    public Page<News> listPublishNewsByNewsName(UserPublishNewsQuery userPublishNewsQuery, int pageIndex, int pageSize) {
        if(userPublishNewsQuery.getId() < 0) {
            log.info("[INFO][关注列表]用户id错误");
            return null;
        }
        // 开启分页
        Page<News> page = PageHelper.startPage(pageIndex, pageSize);
        List<News> news = new LambdaQueryChainWrapper<>(newsMapper)
                .eq(News::getAuthor, userPublishNewsQuery.getId())
                .like(News::getNewsTitle, userPublishNewsQuery.getNewsName())
                .list();
        if(Objects.isNull(news)) {
            log.info("[INFO][新闻发布列表]用户:{}, 没有查询到新闻发布列表！", userPublishNewsQuery.getId());
        } else {
            log.info("[INFO][新闻发布列表]用户:{}, 查询新闻发布列表成功！", userPublishNewsQuery.getId());
        }
        return page;
    }

    @Override
    public UserQuery getFollowUserByAttentionUserId(FollowUserDTORequest followUserDTORequest) {
        // 获取关注列表
        List<UserQuery> userQueries = listAttentionUsers(followUserDTORequest.getUserId());
        for (UserQuery u : userQueries) {
            if (u.getId().equals(followUserDTORequest.getAttentionUserId())) return u;
        }
        return null;
    }

    @Override
    public boolean followUser(FollowUserDTORequest followUserDTORequest) {
        if(followUserDTORequest == null) {
            log.info("[INFO][关注用户]请求参数错误！");
            return false;
        }
        Integer attentionUserId = followUserDTORequest.getAttentionUserId();
        UserQuery userQuery = userMapper.getUserVoById(attentionUserId);
        if(Objects.isNull(userQuery)) {
            log.info("[INFO][关注用户]被关注用户不存在!");
            return false;
        }
        UserAttention attention = new UserAttention();
        BeanUtils.copyProperties(followUserDTORequest, attention);
        return userAttentionMapper.insert(attention) == 1;
    }

    @Override
    public boolean unfollowUser(FollowUserDTORequest followUserDTORequest) {
        UserQuery userQuery = getFollowUserByAttentionUserId(followUserDTORequest);
        // 尝试取消关注
        return userQuery != null;
    }

    @Override
    public boolean particularUser(FollowUserDTORequest followUserDTORequest) {
        return false;
    }

    @Override
    public boolean banUserById(String id) {
        return new LambdaUpdateChainWrapper<User>(userMapper)
                .set(User::getBan, true)
                .eq(User::getId, id).update();
    }

    @Override
    public boolean unBanUserById(String id) {
        return new LambdaUpdateChainWrapper<User>(userMapper)
                .set(User::getBan, false)
                .eq(User::getId, id).update();
    }

    /**
     * 查询用户 的关注列表
     * @param id
     * @return
     */
    public List<UserQuery> listAttentionUsers (Integer id) {
        return userMapper.listAttentionUser(id);
    }

    /**
     * 根据用户名查询一名用户
     * @param username
     * @return
     */
    public User getOneByUsername(String username) {
        return new LambdaQueryChainWrapper<>(userMapper)
                .eq(User::getUsername, username).one();
    }

    /**
     * 根据手机号查询一名用户
     * @param telephone
     * @return
     */
    public User getOneByTelephone(String telephone) {
        return new LambdaQueryChainWrapper<>(userMapper)
                .eq(User::getTelephone, telephone).one();
    }

}
