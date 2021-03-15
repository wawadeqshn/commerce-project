package com.dxy.commerce.product.constants;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/27 3:51 下午
 */
public class BusinessConstants {

    public final static String OPERATOR_SPLIT_COMMA = ",";
    public final static String OPERATOR_SPLIT_COLON = ":";
    public final static String OPERATOR_SPLIT_UNDER_LINE = "_";

    // JWT验证结果常量
    public final static String SUCCESS = "token验证成功";
    public final static String EXPIRED = "token已过期";
    public final static String SIGNATURE_VERIFICATION = "token签名失败";
    public final static String DECODE_ERROR = "token解析失败，请重新登录获取token";
    public final static String NOT_LOGIN = "当前未登录，请先登陆！";

    // Token错误返回的状态码
    public final static String TOKEN_SUCCESS = "token_success"; // token验证成功
    public final static String TOKEN_IS_NULL = "token_is_null"; // token是空的
    public final static String TOKEN_EXPIRED = "token_expired"; // token已过期
    public final static String TOKEN_SIGNATURE_VERIFICATION = "token_signature_verification"; // token签名失败
    public final static String TOKEN_DECODE_ERROR = "token_decode_error"; // token解析失败，请重新登录获取token
    public final static String TOKEN_NOT_LOGIN = "token_not_login"; // 未登录
    public final static String TOKEN_ILLEGAL = "token_illegal"; // 非法token，请先正常登陆

}
