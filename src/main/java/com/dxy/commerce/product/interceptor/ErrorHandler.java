package com.dxy.commerce.product.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
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
@Log4j2
public class ErrorHandler {

    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回的状态码
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handle(HttpServletRequest req, Exception ex){
        ex.printStackTrace();
        // 这里可根据不同异常引起的类做不同处理方式
        String exceptionName = ClassUtils.getShortName(ex.getClass());
        log.error("ExceptionHandler ===>" + exceptionName);

        Map<String,Object> info=new HashMap<>();
        info.put("message",ex.getMessage());
        info.put("item",new Date().getTime());
        return  info;
    }

}
