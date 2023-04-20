package com.immdream.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token 管理层
 * <p>
 * news com.immdream.security.security
 *
 * @author immDream
 * @date 2023/04/11/23:11
 * @since 1.8
 */
@Slf4j
public class JwtTokenManager {

    /** token有效期为 1天 **/
    private static final long tokenExpiration = 24*60*60*1000;
    private static final String tokenSignKey = "123456";

    /**
     * 创建 Token
     * @param username
     * @return
     */
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public String getUserFromToken(String token) {
        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return user;
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

}