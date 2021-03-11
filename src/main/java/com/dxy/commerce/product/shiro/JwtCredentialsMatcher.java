package com.dxy.commerce.product.shiro;

import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @author xingxing
 */
@Log4j2
public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = (String)authenticationToken.getCredentials();
        Object stored = authenticationInfo.getCredentials();
        String salt = stored.toString();

        String account = (String)authenticationInfo.getPrincipals().getPrimaryPrincipal();

        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("account", account).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error("Token Error:{}", e.getMessage());
        }

        return false;
    }
}
