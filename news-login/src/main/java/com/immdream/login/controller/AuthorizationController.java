package com.immdream.login.controller;

import com.immdream.commons.exception.ErrorCode;
import com.immdream.commons.util.JsonResult;
import com.immdream.commons.util.RedisUtil;
import com.immdream.login.config.LoginProperties;
import com.immdream.login.exception.AuthorizationException;
import com.immdream.login.security.TokenManager;
import com.immdream.login.service.LoginService;
import com.immdream.model.domain.user.request.LoginUserDTORequest;
import com.immdream.webapi.auth.AuthControllerApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 授权、根据token获取用户详细信息
 * <p>
 * news com.immdream.login.controller
 *
 * @author immDream
 * @date 2023/04/11/21:47
 * @since 1.8
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
public class AuthorizationController implements AuthControllerApi {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenManager tokenManager;
    @Resource
    private LoginProperties loginProperties;
    @Resource
    private RedisUtil redis;
    @Autowired
    private LoginService loginService;


    @PostMapping(value = "/login")
    @Override
    public JsonResult<Object> login(@Validated @RequestBody LoginUserDTORequest user) {
        // 查询验证码
        String code = (String) redis.get(user.getVerifyCode());
        // 清除验证码
        redis.del(user.getUsername());
        if (StringUtils.isEmpty(code)) {
            throw new AuthorizationException(ErrorCode.LOGIN_CODE_EXPIRED);
        }
        if(!user.getVerifyCode().equalsIgnoreCase(code)) {
            throw new AuthorizationException(ErrorCode.LOGIN_CODE_ERROR);
        }
        // 生成用户验证Token类
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 身份验证类
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 保存当前用户
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成 token
        String token = tokenManager.createToken(user.getUsername());

        return JsonResult.success();
    }

    @Override
    public JsonResult<java.lang.Object> logout() {
        return null;
    }

    @Override
    public JsonResult<java.lang.Object> getUserJwt() {
        return null;
    }
}
