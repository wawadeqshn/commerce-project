package com.dxy.commerce.product.interceptor;

import com.dxy.commerce.product.common.Result;
import com.dxy.commerce.product.common.ResultCode;
import com.dxy.commerce.product.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明: 全局校验异常处理
 *
 * @author dingxy
 * @date 2021/2/28 12:41 上午
 */
@ControllerAdvice
@Component
@Log4j2
public class GlobalExceptionHandler {

    /**
     * 全局校验异常处理 处理使用@Validated注解时，参数验证错误异常（返400错误码）
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                           HttpServletRequest request) {

        ArrayList<Object> messages = Lists.newArrayList();
        if (e.getBindingResult().hasErrors()) {

            List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                messages.add(fieldError.getDefaultMessage());
            }
        }

        return Result.failure(ResultCode.PARAM_IS_INVALID, messages);

    }

    /**
     * 全局校验异常处理,写在controller，用单个或多个参数直接校验时
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected Result handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {

        ArrayList<Object> messages = Lists.newArrayList();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            messages.add(violation.getMessage());

        }
        return Result.failure(ResultCode.PARAM_IS_INVALID, messages);

    }


//  /**
//   * 主要用于shrio的异常类型
//   */
//  @ExceptionHandler(AuthenticationException.class)
//  @ResponseBody
//  public Result authenticationException(AuthenticationException e) {
//
//    return Result.failure(ResultCode.USER_LOGIN_ERROR);
//  }
//
//  /**
//   * 主要用于shrio的异常类型
//   */
//  @ExceptionHandler(UnauthenticatedException.class)
//  @ResponseBody
//  public Result unauthenticatedException() {
//
//    return Result.failure(ResultCode.USER_LOGIN_ERROR);
//  }
//
//  /**
//   * 主要用于shrio的异常类型
//   */
//  @ExceptionHandler(UnauthorizedException.class)
//  @ResponseBody
//  public Result unauthorizedException() {
//    return Result.failure(ResultCode.PERMISSION_NO_ACCESS);
//  }
//
//  /**
//   * 主要用于shrio的异常类型
//   */
//  @ExceptionHandler(UnknownAccountException.class)
//  @ResponseBody
//  public Result unknownAccountException() {
//
//    return Result.failure(ResultCode.USER_LOGIN_ERROR);
//  }

    /**
     * 请求无效或为空
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求无效或为空", e);
        return Result.failure(ResultCode.PARAM_IS_INVALID);
    }

    /**
     * 请求类型错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result httpRequestMethodNotSupportedException() {
        return Result.failure(ResultCode.PARAM_QUERY_TYPE_WRONG);
    }


    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result businessException(BusinessException e) {

        return Result.failure(ResultCode.SENSEDEAL_QUESTIONED, e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result systemException(Exception e) {

        return Result.failure(ResultCode.SENSEDEAL_QUESTIONED, e.getMessage());
    }
}