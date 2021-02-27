package com.dxy.commerce.product.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明: 全局异常处理,接管异常处理,不再走spring默认的异常处理
 *
 * @author dingxy
 * @date 2021/2/27 10:16 下午
 */
@ControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回的状态码
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handle(Exception ex){
        Map<String,Object> info=new HashMap<>();
        info.put("message",ex.getMessage());
        info.put("item",new Date().getTime());
        return  info;
    }
}
