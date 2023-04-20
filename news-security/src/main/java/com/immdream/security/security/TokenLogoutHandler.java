package com.immdream.security.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出业务逻辑
 * <p>
 * news com.immdream.security.security
 *
 * @author immDream
 * @date 2023/04/11/23:14
 * @since 1.8
 */
public class TokenLogoutHandler implements LogoutHandler {
    private JwtTokenManager jwtTokenManager;

    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(JwtTokenManager jwtTokenManager, RedisTemplate redisTemplate) {
        this.jwtTokenManager = jwtTokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {
            jwtTokenManager.removeToken(token);

            //清空当前用户缓存中的权限数据
            String userName = jwtTokenManager.getUserFromToken(token);
            redisTemplate.delete(userName);
        }
    }
}
