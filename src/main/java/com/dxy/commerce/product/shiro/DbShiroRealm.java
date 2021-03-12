//package com.dxy.commerce.product.shiro;
//
//
//import com.dxy.commerce.product.dto.UserDTO;
//import com.dxy.commerce.product.service.account.IUserService;
//import lombok.extern.log4j.Log4j2;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.crypto.hash.Sha256Hash;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//
///**
// * @author xingxing
// */
//@Log4j2
//public class DbShiroRealm extends AuthorizingRealm {
//
//    private IUserService niceUserService;
//
//    public DbShiroRealm(IUserService niceUserService) {
//        this.niceUserService = niceUserService;
//        // 因为数据库中的密码做了散列，所以使用shiro的散列Matcher
//        this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
//    }
//
//    /**
//     * 找它的原因是这个方法返回true
//     */
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof UsernamePasswordToken;
//    }
//
//    /**
//     * 这一步我们根据token给的用户名，去数据库查出加密过用户密码，然后把加密后的密码和盐值一起发给shiro，让它做比对
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        UsernamePasswordToken userPasswordToken = (UsernamePasswordToken)token;
//        String userAccount = userPasswordToken.getUsername();
//        UserDTO user = niceUserService.getUserInfo(userAccount);
//
//        return new SimpleAuthenticationInfo(user, user.getEncryptPwd(), ByteSource.Util.bytes(user.getDbSalt()),
//                "dbRealm");
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//
//        // String userName = (String) principals.getPrimaryPrincipal();
//        //
//        // // List<String> roles = proprietorService.getUserRoles(userName);
//        // List<String> roles = null;
//        //
//        // if (roles != null) {
//        // simpleAuthorizationInfo.addRoles(roles);
//        // }
//        return simpleAuthorizationInfo;
//    }
//}
