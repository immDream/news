package com.immdream.usermanager.controller;


import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.BaseDTO;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.dto.UserDTO;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.LoginUserDTORequest;
import com.immdream.model.domain.user.request.RegisterUserDTORequest;
import com.immdream.model.domain.user.request.UserUpdateDTORequest;
import com.immdream.usermanager.service.IUserService;
import com.immdream.webapi.user.UserManagerApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * <p>
 *  用户管理服务
 * </p>
 *
 * @author immDream
 * @since 2023-04-11
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class UserManagerController implements UserManagerApi {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录（免验证）
     * @param loginUserDTORequest
     * @return
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginUserDTORequest loginUserDTORequest) {
        if(Objects.isNull(loginUserDTORequest)) return JsonResult.create(HttpStatus.BAD_REQUEST, "用户名或密码错误", ErrorCode.REQUEST_PARAM_INVALID.getCode(), ErrorCode.REQUEST_PARAM_INVALID.getMessage());
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(loginUserDTORequest, userDTO);
        log.info("[INFO][用户登录] {}用户登录中!", userDTO.getUsername());
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
    public JsonResult addUser(@RequestBody @Validated({BaseDTO.Insert.class}) UserUpdateDTORequest user) {
        RegisterUserDTORequest registerUserDTORequest = new RegisterUserDTORequest();
        BeanUtils.copyProperties(user, registerUserDTORequest);
        return registerUser(registerUserDTORequest);
    }

    /**
     * 用户删除 - 返回被删除的用户名
     * @param username 要删除的用户名
     * @return
     */
    @Override
    @DeleteMapping("/delete_user/{username}")
    public JsonResult deleteUser(@PathVariable String username) {
        log.info("[INFO][用户删除] 用户名：{}，正在进行删除！", username);
        if(StringUtils.isEmpty(username)) {
            log.info("[INFO][用户删除] 用户名为空！");
            return JsonResult.error(ErrorCode.REQUEST_PARAM_INVALID, "请求用户名不能为空!");
        }
        boolean finish = userService.deleteUserByUsername(username);
        if(!finish) {
            log.info("[INFO][用户删除] 用户名：{}，删除业务失败！", username);
            return JsonResult.error(ErrorCode.ADMIN_DELETE_USER_ERROR, "用户删除失败!");
        }
        log.info("[INFO][用户删除] 用户名：{}，删除成功！", username);
        return JsonResult.success("用户删除成功！", username);
    }

    /**
     * 更改用户信息
     * @param id 要更改的用户的 id
     * @param newUserInfo 更改后的用户信息
     * @return
     */
    @Override
    @PutMapping("/update_user_info/{id}")
    public JsonResult updateUserInfo(@PathVariable Integer id,
                                     @RequestBody @Validated({BaseDTO.Update.class}) UserUpdateDTORequest newUserInfo) {
        if(id == 0 || Objects.isNull(newUserInfo))
            return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "要修改的用户参数为空!");
        // if(StringUtils.isEmpty(newUserInfo.getTelephone())) return JsonResult.error(ErrorCode.REQUEST_PARAM_ERROR, "用户手机号禁止为空！");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(newUserInfo, userDTO);
        // 更新用户信息
        boolean finish = userService.updateUserInfoById(userDTO);
        if(!finish) {
            log.info("[INFO][用户更新]用户:{}, 信息更新失败!", newUserInfo.getUsername());
        }
        return JsonResult.success("用户信息更新成功!");
    }

    @Override
    @GetMapping("/queryUser")
    public JsonResult getUser(Integer id) {
        if(id <= 0) return JsonResult.success("用户不存在!");
        return JsonResult.success(userService.getById(id));
    }

    @Override
    @GetMapping("/getUser")
    public JsonResult<Object> getUser(UserQuery userQuery) {
        return null;
    }

    @Override
    @GetMapping("/getUserList")
    public JsonResult<Object> getUserList() {
        return null;
    }
}
