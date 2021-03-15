package com.dxy.commerce.product.controller;

import com.dxy.commerce.product.common.Result;
import com.dxy.commerce.product.common.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/3/15 9:11 下午
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    /**
     * 未登录，没有token， 以及其他关于token信息错误，会重定向到这个方法来
     * @param msg
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/error")
    public Result error(String msg) throws UnsupportedEncodingException {
        System.out.println("---------------error----------------"+msg);
        return Result.failure(ResultCode.TOKEN_IS_ERROR.code(),new String(msg.getBytes(), "UTF-8"));
    }
}
