package com.dxy.commerce.product.common;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 公共返回形式定义
 * @author dingxy
 * @date 2021/2/27 3:49 下午
 * @return
 */
@Data
@Log4j2
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private Integer ret_code;

    private String ret_msg;

    private T ret_data;

    public Result() {}

    public Result(Integer code, String msg) {
        this.ret_code = code;
        this.ret_msg = msg;
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setRet_data(data);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setRet_data(data);
        return result;
    }

    public static Result failure(Integer code, String message) {
        Result result = new Result();
        result.setRet_code(code);
        result.setRet_msg(message);
        return result;
    }

    public static Result failure(Integer code, String message, Object data) {
        Result result = new Result();
        result.setRet_code(code);
        result.setRet_msg(message);
        result.setRet_data(data);
        return result;
    }

    public void setResultCode(ResultCode code) {
        this.ret_code = code.code();
        this.ret_msg = code.message();
    }

    /**
     * 将异常信息写到response中
     *
     * @param response
     * @param code
     */
    public static void writeErrorInfoToResponse(ServletResponse response, ResultCode code) {

        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String contentType = "application/json;charset=UTF-8";
        httpResponse.setContentType(contentType);
        try {
            PrintWriter printWriter = httpResponse.getWriter();
            printWriter.append(new Gson().toJson(Result.failure(code)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
