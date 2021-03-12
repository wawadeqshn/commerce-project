//package com.dxy.commerce.product.shiro;
//
//
//import com.dxy.commerce.product.common.Result;
//import com.dxy.commerce.product.common.ResultCode;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.authz.AuthorizationFilter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import java.io.IOException;
//
///**
// *
// * @author dingxy
// * @date 2021/2/28 12:34 上午
// * @return
// */
//public class AnyRolesAuthorizationFilter extends AuthorizationFilter {
//
//    @Override
//    protected void postHandle(ServletRequest request, ServletResponse response) {
//        request.setAttribute("anyRolesAuthFilter.FILTERED", true);
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
//                                      Object mappedValue) throws Exception {
//        Subject subject = getSubject(servletRequest, servletResponse);
//        String[] rolesArray = (String[])mappedValue;
//        if (rolesArray == null || rolesArray.length == 0) {
//            // 没有角色限制，有权限访问
//            return true;
//        }
//        for (String role : rolesArray) {
//            if (subject.hasRole(role)) {
//                // 若当前用户是rolesArray中的任何一个，则有权限访问
//                return true;
//            }
//
//        }
//        return false;
//    }
//
//    /**
//     * 权限校验失败，错误处理
//     */
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
//        Result.writeErrorInfoToResponse(response, ResultCode.PERMISSION_NO_ACCESS);
//        return false;
//    }
//}
