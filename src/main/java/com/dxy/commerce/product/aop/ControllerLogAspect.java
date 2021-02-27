package com.dxy.commerce.product.aop;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 功能说明:
 *  申明是个切面 申明是个spring管理的bean 全局日志记录工具类 切面是在controller层
 * @author dingxy
 * @date 2021/2/27 4:48 下午
 */
@Aspect
@Component
@Order(1)
@Log4j2
public class ControllerLogAspect {

    private Gson gson = new Gson();

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 环绕通知
     *
     * @param joinPoint 连接点
     * @return 切入点返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {


        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        // 打印请求内容
        log.info("+++++++++++++++++++++++++++++【开始】+++++++++++++++++++++++++++++");
        log.info("===============请求内容===============");
        log.info("请求地址:" + request.getRequestURL().toString());
        log.info("请求方式:" + request.getMethod());
        log.info("请求类方法:" + joinPoint.getSignature());
        log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        log.info("===============请求内容===============");
        Object result = joinPoint.proceed();

        String resultJson = gson.toJson(result);

        log.info("--------------------------------");
        log.info("--------------------------------");
        log.info("--------------返回内容----------------");
        log.info("Response内容:" + resultJson);
        log.info("--------------返回内容----------------");

        log.info("请求处理时间为: " + (System.currentTimeMillis() - startTime.get()) +" 毫秒");
        log.info("+++++++++++++++++++++++++++++【结束】+++++++++++++++++++++++++++++");
        startTime.remove();
        return result;
    }
}
