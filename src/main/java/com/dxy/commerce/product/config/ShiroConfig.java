package com.dxy.commerce.product.config;


import com.dxy.commerce.product.service.account.IUserService;

import com.dxy.commerce.product.shiro.AnyRolesAuthorizationFilter;
import com.dxy.commerce.product.shiro.DbShiroRealm;
import com.dxy.commerce.product.shiro.JwtAuthFilter;
import com.dxy.commerce.product.shiro.JwtShiroRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;

import static com.dxy.commerce.product.constants.SystemConstant.SHRIO_ANNO_AND_NOSESSION;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/27 10:34 下午
 */
@Configuration
public class ShiroConfig {

    /**
     * 注册shiro的Filter，拦截请求
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager, IUserService userService) throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter((Filter)shiroFilter(securityManager, userService).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);

        return filterRegistration;
    }

    /**
     * 初始化Authenticator
     */
    @Bean
    public Authenticator authenticator(IUserService userService) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        // 设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(userService), dbShiroRealm(userService)));
        // 设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * 用于用户名密码登录时认证的realm
     */
    @Bean("dbRealm")
    public Realm dbShiroRealm(IUserService userService) {
        return new DbShiroRealm(userService);
    }

    /**
     * 用于JWT token认证的realm
     */
    @Bean("jwtRealm")
    public Realm jwtShiroRealm(IUserService userService) {
        return new JwtShiroRealm(userService);
    }

    /**
     * 设置过滤器，将自定义的Filter加入
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, IUserService userService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", createAuthFilter(userService));
        filterMap.put("anyRole", createRolesFilter());
        factoryBean.setFilters(filterMap);
        // factoryBean.setUnauthorizedUrl("user/403");
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        return factoryBean;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/actuator/prometheus", SHRIO_ANNO_AND_NOSESSION);
        // chainDefinition.addPathDefinition("/user/getKaptcha", SHRIO_ANNO);
        // chainDefinition.addPathDefinition("/user/validatCaptcha", SHRIO_ANNO);
        // // 做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
        // chainDefinition.addPathDefinition("/user/logout", SHRIO_NO_TOKEN_PASS);
        // chainDefinition.addPathDefinition("/user/reset", SHRIO_NO_TOKEN_PASS);
        // // 只允许admin或manager角色的用户访问
        // chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]");
        // chainDefinition.addPathDefinition("/druid/**", SHRIO_NO_TOKEN_PASS);
        //
        // //为微服务配置的
        // chainDefinition.addPathDefinition("/api/**",SHRIO_ANNO_AND_NOSESSION);
        // 默认进行用户鉴权
        // chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken");
        return chainDefinition;
    }

    /**
     * 注意不要加@Bean注解，不然spring会自动注册成filter
     * @param userService
     * @return
     */
    protected JwtAuthFilter createAuthFilter(IUserService userService) {
        return new JwtAuthFilter(userService);
    }

    /**
     * 注意不要加@Bean注解，不然spring会自动注册成filter
     * @return
     */
    protected AnyRolesAuthorizationFilter createRolesFilter() {
        return new AnyRolesAuthorizationFilter();
    }

}
