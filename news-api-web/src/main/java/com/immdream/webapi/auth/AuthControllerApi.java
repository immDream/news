package com.immdream.webapi.auth;

import com.immdream.commons.util.JsonResult;
import com.immdream.model.domain.user.request.LoginUserDTORequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 鉴权接口
 * <p>
 * news com.immdream.webapi.auth
 *
 * @author immDream
 * @date 2023/03/13/16:28
 * @since 1.8
 */
@Api(value = "用户认证")
public interface AuthControllerApi {

    @ApiOperation("用户名登录")
    JsonResult<Object> login(LoginUserDTORequest loginUserDTORequest);

    @ApiOperation("退出")
    JsonResult<Object> logout();

    @ApiOperation("查询用户jwt令牌")
    JsonResult<Object> getUserJwt();
}
