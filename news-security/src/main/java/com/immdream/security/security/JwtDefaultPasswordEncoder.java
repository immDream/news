package com.immdream.security.security;

import com.immdream.commons.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码处理方法
 * <p>
 * news com.immdream.security
 *
 * @author immDream
 * @date 2023/04/11/22:49
 * @since 1.8
 */
public class JwtDefaultPasswordEncoder implements PasswordEncoder {

    public JwtDefaultPasswordEncoder() {
        this(-1);
    }

    /**
     * @param strength
     *    the log rounds to use, between 4 and 31
     */
    public JwtDefaultPasswordEncoder(int strength) {

    }

    public String encode(CharSequence rawPassword) {
        return MD5Util.encrypt(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.encrypt(rawPassword.toString()));
    }
}