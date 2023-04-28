// package com.immdream.gateway.service;
//
// import com.immdream.commons.util.CookieUtil;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Service;
// import org.springframework.util.StringUtils;
//
// import javax.annotation.Resource;
// import javax.servlet.http.HttpServletRequest;
// import java.util.Map;
// import java.util.concurrent.TimeUnit;
//
// /**
//  * 登录验证
//  * <p>
//  * news com.immdream.gateway
//  *
//  * @author immDream
//  * @date 2023/04/25/21:22
//  * @since 1.8
//  */
// @Service
// public class AuthService {
//
//     @Resource
//     StringRedisTemplate stringRedisTemplate;
//
//     /**
//      * 从头取出jwt令牌
//      *
//      * @param request
//      * @return
//      */
//     public String getJwtFromHeader(HttpServletRequest request) {
//         //取出头信息
//         String authorization = request.getHeader("Authorization");
//         if (StringUtils.isEmpty(authorization)) {
//             return null;
//         }
//         if (!authorization.startsWith("Bearer ")) {
//             return null;
//         }
//         //取到jwt令牌
//         String jwt = authorization.substring(7);
//         return jwt;
//     }
//
//     /**
//      * 从cookie取出token,查询身份令牌
//      *
//      * @param request
//      * @return
//      */
//     public String getTokenFromCookie(HttpServletRequest request) {
//         Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
//         String access_token = cookieMap.get("uid");
//         if (StringUtils.isEmpty(access_token)) {
//             return null;
//         }
//         return access_token;
//     }
//
//     /**
//      * 查询令牌的有效期
//      *
//      * @param access_token
//      * @return
//      */
//     public long getExpire(String access_token) {
//         //key
//         String key = "user_token:" + access_token;
//         Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
//         return expire;
//     }
// }