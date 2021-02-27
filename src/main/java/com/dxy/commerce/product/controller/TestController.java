package com.dxy.commerce.product.controller;

import com.dxy.commerce.product.client.request.IndustryMonitorAddReq;
import com.dxy.commerce.product.common.Result;
import com.dxy.commerce.product.common.ResultCode;
import com.dxy.commerce.product.domain.TestTable;
import com.dxy.commerce.product.service.ITestTableService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/27 2:56 下午
 */
@Log4j2
@RestController
public class TestController {

    @Autowired
    private ITestTableService tableService; // 测试表

    // http://localhost:5000/test?monitor_type=1&model_class=010&uid=1
    @ApiOperation(value = "测试")
    @GetMapping("/test")
    public Result<List<TestTable>> call(@Validated IndustryMonitorAddReq req, HttpServletRequest request) {
        try {
            List<TestTable> result = tableService.getAll();
            return Result.success(result);
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }
}
