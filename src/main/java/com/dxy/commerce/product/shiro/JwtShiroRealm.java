package com.dxy.commerce.product.shiro;

import com.dxy.commerce.product.dto.UserDTO;
import com.dxy.commerce.product.service.account.IUserService;
import com.dxy.commerce.product.utils.JwtUtils;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author xingxing
 */
@Log4j2
public class JwtShiroRealm extends AuthorizingRealm {

    protected IUserService niceUserService;

    public JwtShiroRealm(IUserService userService) {
        this.niceUserService = userService;
        // 这里使用我们自定义的Matcher
        this.setCredentialsMatcher(new JwtCredentialsMatcher());
    }

    /**
     * 限定这个Realm只支持我们自定义的JWT Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 跟controller登录一样，也是获取用户的salt值，给到shiro，由shiro来调用matcher来做认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) {
        JwtToken jwtToken = (JwtToken)authToken;
        String token = jwtToken.getToken();

        UserDTO user = niceUserService.getJwtTokenInfo(JwtUtils.getUsername(token));
        if (user == null) {
            throw new AuthenticationException("token过期，请重新登录");
        }

        return new SimpleAuthenticationInfo(user.getAccount(), user.getTokenSalt(), "jwtRealm");

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }
}
