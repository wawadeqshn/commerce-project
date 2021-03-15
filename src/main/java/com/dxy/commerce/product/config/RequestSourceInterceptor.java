package com.dxy.commerce.product.config;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BaseGlobalInterceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 请求头中添加公共参数，例如 authorization 等请求字段的值
 * @author dingxy
 * @date 2021/2/27 3:40 下午
 * @return
 */
@Component
public class RequestSourceInterceptor extends BaseGlobalInterceptor {
    @Value("${cmb.dgw.api.head}")
    String request_head;

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newReq = request.newBuilder()
                .addHeader("authorization", request_head)
                .build();
        return chain.proceed(newReq);
    }
}
