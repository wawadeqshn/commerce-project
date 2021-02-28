package com.dxy.commerce.product.constants;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/28 12:29 上午
 */
public class SystemConstant {

    private SystemConstant() {
        throw new IllegalStateException("SystemConstant class");
    }

    /**
     * jwt token失效时长 设置过期时间为6小时
     */
    public static final Integer TOKEN_EXPIRES_SECONDS = 3600 * 6;

    public static final Integer TOKEN_REFRESH_INTERVAL_SECONDS = 300;

    /**
     * token的盐值 缓存
     */
    public static final String REDIS_KEY_TOKEN_SALT = "nice_token_salt:";

    /**
     * redis缓存超时天数
     */
    public static final Integer TTL_DAYS = 7;

    /**
     * shiro 权限设置 permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
     */
    public static final String SHRIO_NO_TOKEN_PASS = "noSessionCreation,authcToken[permissive]";

    /**
     * login等不做认证，noSessionCreation的作用是用户在操作session时会抛异常
     */
    public static final String SHRIO_ANNO_AND_NOSESSION = "noSessionCreation,anon";
}
