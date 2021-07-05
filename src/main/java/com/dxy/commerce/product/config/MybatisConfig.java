package com.dxy.commerce.product.config;

import com.baomidou.mybatisplus.extension.plugins.*;
import org.mybatis.spring.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;

@EnableTransactionManagement
@Configuration
@MapperScan("com.dxy.commerce.product.mapper")
public class MybatisConfig {

    /**
     * mybatis-plus 分页插件
     * @author dingxy
     * @date 2021/2/27 2:58 下午
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}