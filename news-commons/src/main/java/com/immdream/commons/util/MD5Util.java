package com.immdream.commons.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.commons.util
 *
 * @author immDream
 * @date 2023/04/11/22:55
 * @since 1.8
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /* 和前端进行统一，通过salt获取前端加密过的密码 */
    private static final String salt = "1a2b3c4d";

    public static String encrypt(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        // System.out.println(str);
        return md5(str);
    }

    /**
     * 根据对应盐加密数据
     * @param formPass
     * @param salt 根据盐
     * @return
     */
    public static String encrypt(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
}
