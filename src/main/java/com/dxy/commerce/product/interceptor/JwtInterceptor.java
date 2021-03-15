package com.dxy.commerce.product.interceptor;

import com.dxy.commerce.product.config.JwtConfigProperties;
import com.dxy.commerce.product.constants.BusinessConstants;
import com.dxy.commerce.product.utils.JWTUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 功能说明: 拦截器类
 *
 * @author dingxy
 * @date 2021/3/11 9:52 下午
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("-----------3--------------");
        // 这里是个坑，因为带请求带headers时，ajax会发送两次请求，
        // 第一次会发送OPTIONS请求，第二次才会发生get/post请求，所以要放行OPTIONS请求
        // 如果是OPTIONS请求，让其响应一个 200状态码，说明可以正常访问
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            // 放行OPTIONS请求
            return true;
        }

        // JwtConfigProperties通过@Autowired的值为null，所以需要通过以下方法获取bean
        if(jwtConfigProperties == null){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            jwtConfigProperties = (JwtConfigProperties) factory.getBean("jwtConfigProperties");
        }
        /*boolean verify = false;
        String errMsg = "";
        // 获取header中的token
        String token = request.getHeader("Authorization");
        try {
            verify = JWTUtil.verify(token, jwtConfigProperties);
        } catch (Exception e) {
            errMsg = e.getMessage();
            e.printStackTrace();
        }
        // 验证token，如果验证失败就重定向到未登录页面
        if (!verify){
            errMsg = URLEncoder.encode(errMsg, "UTF-8");
            //这里是个坑，在重定向这里需要设置跨域，不然vue前端会报跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 重定向到未登录提示页面。如果token验证失败，会调用这个方法。
            response.sendRedirect("/common/error?msg=" + errMsg);
            return false;
        }*/

        String verify = BusinessConstants.TOKEN_SUCCESS; // token是否验证通过，如果通过则没有异常token信息返回。默认token验证成功
        String errMsg = ""; // 返回错误的message信息
        // 获取header中的token
        String token = request.getHeader("Authorization");
        try {
            verify = JWTUtil.verify(token, jwtConfigProperties);
        } catch (Exception e) {
            //errMsg = e.getMessage();  // 不再从异常中获取返回的 报错token信息
            e.printStackTrace();
        }
        errMsg = verify; // 不再从异常中获取返回的 报错token信息。直接获取返回的验证错误码
        // 验证token，如果验证失败就重定向到common/error 方法
        if (!(verify.equals(BusinessConstants.TOKEN_SUCCESS))){ // 说明有错误信息
            errMsg = URLEncoder.encode(errMsg, "UTF-8");
            //这里是个坑，在重定向这里需要设置跨域，不然vue前端会报跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 重定向到未登录提示页面。如果token验证失败，会调用这个方法。
            response.sendRedirect("/common/error?msg=" + errMsg);
            return false;
        }
        return true;
    }
}
