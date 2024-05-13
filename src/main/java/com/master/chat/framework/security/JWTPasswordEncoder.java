package com.master.chat.framework.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

/**
 * 自定义密码加密类处理
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0

 */
public class JWTPasswordEncoder implements PasswordEncoder {
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    @Override
    public String encode(CharSequence charSequence) {
        //不做任何加密处理
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        //charSequence是前端传过来的密码，s是数据库中查到的密码
        if (matchesBcrypt(s)) {
            return matchesBcrypt(charSequence.toString(), s);
        } else {
            if (charSequence.toString().equals(s)) {
                return true;
            }
            return false;
        }
    }

    /**
     * 判断是否为Bcrypt密码
     *
     * @param s 判断字符串
     * @return true 是 false 否
     */
    public static boolean matchesBcrypt(String s) {
        return BCRYPT_PATTERN.matcher(s).matches();
    }

    /**
     * 判断是Bcrypt密码是否一致
     *
     * @param plaintext 原文
     * @param hashed    加盐密文
     * @return true 是 false 否
     */
    public static boolean matchesBcrypt(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    /**
     * 加密
     *
     * @param s 加密字符串
     * @return 加密后字符串
     */
    public static String bcryptEncode(String s) {
        return new BCryptPasswordEncoder().encode(s);
    }

}
