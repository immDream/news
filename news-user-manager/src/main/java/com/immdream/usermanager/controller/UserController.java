package com.immdream.usermanager.controller;


import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.BaseDTO;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.request.LoginUserDTORequest;
import com.immdream.model.domain.user.request.RegisterUserDTORequest;
import com.immdream.model.domain.user.request.UserUpdateDTORequest;
import com.immdream.usermanager.service.IUserService;
import com.immdream.webapi.user.UserManagerApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  用户管理服务
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/user")
public class UserController implements UserManagerApi {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录（免验证）
     * @param loginUserDTORequest
     * @return
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginUserDTORequest loginUserDTORequest) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(loginUserDTORequest, userDTO);
        if(userDTO == null) return JsonResult.create(HttpStatus.BAD_REQUEST, "用户名或密码错误", ErrorCode.REQUEST_PARAM_INVALID.getCode(), ErrorCode.REQUEST_PARAM_INVALID.getMessage());
        return userService.login(userDTO);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    @PostMapping("/register")
    public JsonResult registerUser(@Validated({BaseDTO.Insert.class}) @RequestBody RegisterUserDTORequest user) {
        if(user == null) return JsonResult.success("用户注册信息必填项未填写!");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userService.register(userDTO);
    }

    /**
     * 用户添加 - 管理员测试操作
     * @param user
     * @return
     */
    @Override
    @PostMapping("/add_user")
    public JsonResult addUser(UserUpdateDTORequest user) {
        UserDTO addUser = new UserDTO();
        BeanUtils.copyProperties(user, addUser);
        // 保存用户成功
        if(userService.saveUser(addUser)) {
            return JsonResult.success("用户添加成功!");
        }
        return JsonResult.error(ErrorCode.REQUEST_ERROR, "用户添加失败!");
    }

    /**
     * 用户删除
     * @param username 要删除的用户名
     * @return
     */
    @Override
    @DeleteMapping("/delete_user/{username}")
    public JsonResult deleteUser(@PathVariable String username) {
        return null;
    }

    /**
     * 更改用户信息
     * @param username 要更改的用户的用户名
     * @param newUserInfo 更改后的用户信息
     * @return
     */
    @Override
    @PutMapping("/update_user_info/{username}")
    public JsonResult updateUserInfo(@PathVariable String username, UserUpdateDTORequest newUserInfo) {
        return null;
    }
}
