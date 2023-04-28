package com.immdream.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * jwt 工具类
 * <p>
 * news com.immdream.security.utils
 *
 * @author immDream
 * @date 2023/04/24/20:03
 * @since 1.8
 */
@Component
public class JwtUtils {
    private final Long expire;
    private final SecretKey key;
    private final JwtParser parser;

    public JwtUtils(@Value("${jjwt.secret}") String secret, @Value("#{${jjwt.expire}}") Long expire) {
        this.expire = expire;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.parser = Jwts.parserBuilder()
                .setSigningKey(key).build();
    }

    /**
     * token 生成
     * @param claims
     * @return
     */
    public String generateToken(Map<String, String> claims) {
        Date createdTime = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdTime)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parse(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        Jwt parse = parser.parse(token);
        return (Claims) parse.getBody();
    }
}
