package com.immdream.usermanager.service.impl;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.RegisterUserDTORequest;
import com.immdream.usermanager.mapper.UserMapper;
import com.immdream.usermanager.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public JsonResult login(UserDTO loginUser) {
        User user = new LambdaQueryChainWrapper<>(userMapper)
                .eq(User::getUsername, loginUser.getUsername()).one();
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
    public JsonResult register(UserDTO userDTO) {
        User condition = new User();
        BeanUtils.copyProperties(userDTO, condition);
        // 查询是否存在对应用户已经注册
        List<User> loginUsers = selectUserListByCondition(condition);
        if(loginUsers == null || loginUsers.size() < 1) {    // 没有注册过
            // 注册用户 - 保存入数据库
            if(this.saveUser(userDTO)) return JsonResult.success("用户注册成功！");
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

    @Override
    public List<User> listUsers(int pageIndex, int pageSize, UserQuery userQuery) {
        return null;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setCreateTime(new Date());
        return userMapper.insert(user) == 1;
    }
}
