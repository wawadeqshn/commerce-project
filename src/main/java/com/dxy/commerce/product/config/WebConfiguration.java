//package com.dxy.commerce.product.config;
//
//import com.dxy.commerce.product.interceptor.LoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * 功能说明:
// *
// * @author dingxy
// * @date 2021/2/27 3:59 下午
// */
//@Configuration
//public class WebConfiguration extends WebMvcConfigurationSupport {
//
//    @Autowired
//    private ThreadPoolTaskExecutor fengyanExecutor;
//
//    @Autowired
//    private LoginInterceptor loginInterceptor;
//
//
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
//                //.excludePathPatterns("/userBehavior/**","/monitor/message/totalNumTimer")
//                //.excludePathPatterns("/chattel/**")
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**").excludePathPatterns("/static/**");
//        super.addInterceptors(registry);
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*").allowCredentials(true);
//    }
//
//    @Override
//    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        configurer.setTaskExecutor(new ConcurrentTaskExecutor(fengyanExecutor));
//        configurer.setDefaultTimeout(300000);
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
//        // 解决 SWAGGER 404报错
//        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//}
