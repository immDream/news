package com.immdream.webapi.user;

import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.request.RegisterUserDTORequest;
import com.immdream.model.domain.user.request.UserUpdateDTORequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.webapi.user
 *
 * @author immDream
 * @date 2023/04/13/16:25
 * @since 1.8
 */
@Api(value = "用户管理")
public interface UserManagerApi {

    @ApiOperation("用户注册")
    JsonResult<Object> registerUser(RegisterUserDTORequest user);

    @ApiOperation("添加用户")
    JsonResult<Object> addUser(UserUpdateDTORequest user);

    @ApiOperation("删除用户")
    JsonResult<Object> deleteUser(String username);

    @ApiOperation("更新用户信息")
    JsonResult<Object> updateUserInfo(String username, UserUpdateDTORequest newUserInfo);

}
