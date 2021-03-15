package com.dxy.commerce.product.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
//import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能说明: JWT 工具类
 *
 * @author dingxy
 * @date 2021/2/27 10:57 下午
 */
public class JwtUtils {

    private JwtUtils() {
        throw new IllegalStateException("JwtUtils class");
    }


    /**
     * 功能说明: 获得token中的信息无需secret解密也能获得
     * @param token
     * @author dingxy
     * @date 2021/2/27 10:59 下午
     * @return token中包含的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 功能说明: 获得token中的信息无需secret解密也能获得
     * @param token
     * @author dingxy
     * @date 2021/2/27 11:00 下午
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("account").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 功能说明: 生成签名,expireTime后过期
     * @param account  用户名
     * @param salt 盐值
     * @param time 过期时间s
     * @author dingxy
     * @date 2021/2/27 11:01 下午
     * @return 加密的token
     */
    public static String sign(String account, String salt, long time) {
        Date date = new Date(System.currentTimeMillis() + time * 1000);
        Algorithm algorithm = Algorithm.HMAC256(salt);
        // 附带username信息
        return JWT.create().withClaim("account", account).withExpiresAt(date).withIssuedAt(new Date())
                .sign(algorithm);
    }


    /**
     * 功能说明: token是否过期
     * @param token
     * @author dingxy
     * @date 2021/2/27 11:03 下午
     * @return boolean -- true：过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }


    /**
     * 功能说明: 生成随机盐,长度32位
     * @author dingxy
     * @date 2021/2/27 11:03 下午
     * @return java.lang.String
     */
    /*public static String generateSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes(16).toHex();
    }*/
}