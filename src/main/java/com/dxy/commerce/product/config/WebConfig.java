package com.dxy.commerce.product.config;

import com.dxy.commerce.product.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能说明: 配置跨域的类
 *
 * @author dingxy
 * @date 2021/3/11 9:52 下午
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //配置跨域请求
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*");
        System.out.println("-----------跨域拦截--------------1--------------");
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new JwtInterceptor());
        // 需拦截的路径
        interceptorRegistration.addPathPatterns("/**");
        // 需放行的路径
        interceptorRegistration.excludePathPatterns("/**/login", "/**/unlogin","/common/error");
        System.out.println("-----------请求路径拦截--------------2--------------");
    }
}
