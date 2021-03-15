package com.dxy.commerce.product.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.dxy.commerce.product.config.JwtConfigProperties;
import com.dxy.commerce.product.constants.BusinessConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * JWT工具类，用于对 token 进行验证
 * @author dingxy
 * @date 2021/3/15 11:00 下午
 * @return 
 */
public class JWTUtil {

    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public static String verify(String token, JwtConfigProperties jwtConfigProperties) throws Exception {
        try {
            // 请求头中的token是空的
            if(StringUtils.isBlank(token)){
                return BusinessConstants.TOKEN_IS_NULL;  // 传递过来的token是空的
            }
            Algorithm algorithm = Algorithm.HMAC256(jwtConfigProperties.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt);
            return BusinessConstants.TOKEN_SUCCESS;  // token验证成功
        } catch (TokenExpiredException tokenExpiredException){
            System.out.println("token已过期");
            //throw new Exception(BusinessConstants.EXPIRED);
            return BusinessConstants.TOKEN_EXPIRED;   // token已过期
        } catch (SignatureVerificationException signatureVerificationException){
            System.out.println("token签名失败");
            //throw new Exception(BusinessConstants.SIGNATURE_VERIFICATION);
            return BusinessConstants.TOKEN_SIGNATURE_VERIFICATION;   // token签名失败
        } catch (JWTDecodeException jwtDecodeException){
            System.out.println("token解析失败，请重新登录获取token");
            //throw new Exception(BusinessConstants.DECODE_ERROR);
            return BusinessConstants.TOKEN_DECODE_ERROR;   // token解析失败，请重新登录获取token IllegalArgumentException
        } catch (IllegalArgumentException illegalArgumentException){
            System.out.println("非法token，请先登录！");
            //throw new Exception(BusinessConstants.DECODE_ERROR);
            return BusinessConstants.TOKEN_ILLEGAL;   // 非法token，请先正常登陆
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("未登录");
            //throw new Exception(BusinessConstants.NOT_LOGIN);
            return BusinessConstants.TOKEN_NOT_LOGIN;   // 未登录
        }
    }

    /*public static boolean verify(String token, JwtConfigProperties jwtConfigProperties) throws Exception {
        try {
            // 请求头中的token是空的
            if(StringUtils.isBlank(token)){
                //throw new RuntimeException(BusinessConstants.NOT_LOGIN);
                return false;
            }

            Algorithm algorithm = Algorithm.HMAC256(jwtConfigProperties.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt);
            return true;
        } catch (TokenExpiredException tokenExpiredException){
            System.out.println("token已过期");
            //throw new Exception(BusinessConstants.EXPIRED);
            throw new RuntimeException(BusinessConstants.EXPIRED);
        } catch (SignatureVerificationException signatureVerificationException){
            System.out.println("token签名失败");
            //throw new Exception(BusinessConstants.SIGNATURE_VERIFICATION);
            throw new RuntimeException(BusinessConstants.SIGNATURE_VERIFICATION);
        } catch (JWTDecodeException jwtDecodeException){
            System.out.println("token解析失败，请重新登录获取token");
            //throw new Exception(BusinessConstants.DECODE_ERROR);
            throw new RuntimeException(BusinessConstants.DECODE_ERROR);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("未登录");
            //throw new Exception(BusinessConstants.NOT_LOGIN);
            throw new RuntimeException(BusinessConstants.NOT_LOGIN);
        }
    }*/

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @return 加密的token
     */
    public static String sign(String username, JwtConfigProperties jwtConfigProperties) {
        Date date = new Date(System.currentTimeMillis()+jwtConfigProperties.getExpire());
        Algorithm algorithm = Algorithm.HMAC256(jwtConfigProperties.getSecret());
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
