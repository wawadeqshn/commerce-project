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
    public final static String NOT_LOGIN = "未登录";

}
