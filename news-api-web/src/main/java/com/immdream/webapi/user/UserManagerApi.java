package com.immdream.webapi.user;

import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.Admin;
import com.immdream.model.domain.user.User;
import com.immdream.model.domain.user.dto.AdminDTO;
import com.immdream.model.domain.user.dto.HistoryDTO;
import com.immdream.model.domain.user.query.UserQuery;
import com.immdream.model.domain.user.request.RegisterUserDTORequest;
import com.immdream.model.domain.user.request.UserUpdateDTORequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.webapi.user
 *
 * @author immDream
 * @date 2023/03/13/16:25
 * @since 1.8
 */
@Api(value = "用户管理")
public interface UserManagerApi {

    @ApiOperation("用户注册")
    JsonResult<Object> registerUser(RegisterUserDTORequest user);

    @ApiOperation("添加用户")
    JsonResult<Object> addUser(UserUpdateDTORequest user);

    @ApiOperation("封禁用户")
    JsonResult<Object> deleteUser(String username);

    @ApiOperation("更新用户")
    JsonResult<Object> updateUserInfo(Integer id, UserUpdateDTORequest newUserInfo);

    @ApiOperation("获取用户")
    JsonResult<Object> getUser(Integer id);

    @ApiOperation("查询对应用户")
    JsonResult<Object> getUser(String keyword, String condition);

    @ApiOperation("查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数")
    })
    JsonResult<Object> getUserList(int pageIndex, int pageSize);

    @ApiOperation("添加用户")
    JsonResult<Object> addAdmin(AdminDTO admin);

    @ApiOperation("删除用户")
    JsonResult<Object> deleteAdmin(Integer id);

    @ApiOperation("更改用户信息")
    JsonResult<Object> updateAdminRole(AdminDTO admin);

    @ApiOperation("获取管理员列表")
    JsonResult<Object> getAdminList(int pageIndex, int pageSize);

    @ApiOperation("封禁用户")
    JsonResult<Object> banUser(String id);

    @ApiOperation("解禁用户")
    JsonResult<Object> unBanUserInfo(String id);

    @ApiOperation("添加或更新用户行为记录")
    JsonResult<Object> historyRecord(HistoryDTO historyDTO);

    @ApiOperation("获取用户行为记录")
    JsonResult<Object> getHistoryRecord(HistoryDTO historyDTO);
}
