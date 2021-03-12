//package com.dxy.commerce.product.shiro;
//
//
//import com.dxy.commerce.product.common.Result;
//import com.dxy.commerce.product.common.ResultCode;
//import com.dxy.commerce.product.constants.SystemConstant;
//import com.dxy.commerce.product.service.account.IUserService;
//import com.dxy.commerce.product.utils.JwtUtils;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
///**
// * @author xingxing
// */
//@Log4j2
//public class JwtAuthFilter extends AuthenticatingFilter {
//
//    private IUserService niceUserService;
//
//    public JwtAuthFilter(IUserService userService) {
//        this.niceUserService = niceUserService;
//    }
//
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//
//        // 这里很坑，druid如果不加下面这句就显示不出监控页面
//        // request.setAttribute(DefaultSubjectContext.SESSION_CREATION_ENABLED, Boolean.TRUE);
//        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
//        // 对于OPTION请求做拦截，不做token校验
//        return !httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())
//                && super.preHandle(request, response);
//
//    }
//
//    @Override
//    protected void postHandle(ServletRequest request, ServletResponse response) {
//        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
//        request.setAttribute("jwtShiroFilter.FILTERED", true);
//    }
//
//    /**
//     * 父类会在请求进入拦截器后调用该方法，返回true则继续， 返回false则会调用onAccessDenied()。 这里在不通过时，还调用了isPermissive()方法。
//     */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        if (this.isLoginRequest(request, response)) {
//            return true;
//        }
//
//        boolean allowed = false;
//        try {
//            allowed = executeLogin(request, response);
//        } catch (IllegalStateException e) {
//            log.error("Not found any TOKEN");
//        } catch (Exception e) {
//            log.error("Error occurs when login", e);
//        }
//        return allowed || super.isPermissive(mappedValue);
//    }
//
//    /**
//     * 这里重写了父类的方法，使用我们自己定义的Token类，提交给shiro。 这个方法返回null的话会直接抛出异常，进入isAccessAllowed（）的异常处理逻辑。
//     */
//    @Override
//    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
//        String jwtToken = getAuthzHeader(servletRequest);
//        if (StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken)) {
//            return new JwtToken(jwtToken);
//        }
//
//        return null;
//    }
//
//    /**
//     * 如果这个Filter在之前isAccessAllowed（）方法中返回false,则会进入这个方法。我们这里直接返回错误的response
//     */
//    @Override
//    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//        Result.writeErrorInfoToResponse(servletResponse, ResultCode.PERMISSION_NO_ACCESS);
//        return false;
//    }
//
//    /**
//     * 如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
//     */
//    @Override
//    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
//                                     ServletResponse response) throws Exception {
//        HttpServletResponse httpResponse = WebUtils.toHttp(response);
//        String newToken = null;
//        if (token instanceof JwtToken) {
//            JwtToken jwtToken = (JwtToken)token;
//            // String account = (String) subject.getPrincipal();
//            boolean shouldRefresh = shouldTokenRefresh(JwtUtils.getIssuedAt(jwtToken.getToken()));
//            if (shouldRefresh) {
//                // newToken = proprietorService.generateJwtToken(account);
//            }
//        }
//        if (StringUtils.isNotBlank(newToken)) {
//            httpResponse.setHeader("x-auth-TOKEN", newToken);
//        }
//
//        return true;
//    }
//
//    /**
//     * 如果调用shiro的login认证失败，会回调这个方法，这里我们什么都不做，因为逻辑放到了onAccessDenied（）中。
//     */
//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
//                                     ServletResponse response) {
//        log.error("Validate TOKEN fail, TOKEN:{}, error:{}", token.toString(), e.getMessage());
//        return false;
//    }
//
//    protected String getAuthzHeader(ServletRequest request) {
//        HttpServletRequest httpRequest = WebUtils.toHttp(request);
//        String header = httpRequest.getHeader("x-auth-TOKEN");
//        return StringUtils.removeStart(header, "Bearer ");
//    }
//
//    protected boolean shouldTokenRefresh(Date issueAt) {
//        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
//        return LocalDateTime.now().minusSeconds(SystemConstant.TOKEN_REFRESH_INTERVAL_SECONDS).isAfter(issueTime);
//    }
//
//    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers",
//                httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//
//    }
//
//}
