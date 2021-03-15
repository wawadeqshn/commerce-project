package com.dxy.commerce.product.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Token状态枚举
 * @author dingxy
 * @date 2021/3/15 10:28 下午 
 * @return 
 */
public enum TokenStatusEnums {

    TOKEN_SUCCESS("token_success","token验证成功",80001),
    TOKEN_IS_NULL("token_is_null","token是空的，用户未登录，请先登陆！",80002),
    TOKEN_EXPIRED("token_expired", "token已过期，请重新登陆！",80003),
    TOKEN_SIGNATURE_VERIFICATION("token_signature_verification","token签名失败，请重试！",80004),
    TOKEN_DECODE_ERROR("token_decode_error","token解析失败，请重新登录获取token！",80005),
    TOKEN_NOT_LOGIN("token_not_login","用户未登录，请先登陆！",80006),
    TOKEN_ILLEGAL("token_illegal","非法token，请先正常登陆！",80007);

    private String code; // token错误类型
    private String message; // 返回的错误信息
    private int number; // 返回的状态码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    TokenStatusEnums(String code, String message, int number) {
        this.code = code;
        this.message = message;
        this.number = number;
    }

    public static TokenStatusEnums getTokenStatusEnumByCode(String code) {
        if(StringUtils.isBlank(code)){
            code = "token_not_login";
        }
        for( TokenStatusEnums item : TokenStatusEnums.values()) {
            if(item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
