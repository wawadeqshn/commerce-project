package com.dxy.commerce.product.controller;

import com.dxy.commerce.product.common.ApiJson;
import com.dxy.commerce.product.config.JwtConfigProperties;
import com.dxy.commerce.product.domain.TestTable;
import com.dxy.commerce.product.domain.UserInfo;
import com.dxy.commerce.product.service.ITestTableService;
import com.dxy.commerce.product.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * http://localhost:8080/user/get/all
 *
 * http://localhost:8080/user/login?username=test&password=123456
 *
 * @author dingxy
 * @date 2021/2/28 11:12 下午 
 * @return 
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", tags = "用户接口")
public class UserController {

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Autowired
    private ITestTableService tableService; // 测试表

    //@PostMapping("/login")
    @GetMapping("/login")
    public ApiJson login(String username, String password){
        /*User user = userService.login(username, password);
        if (user != null){
            // 登录成功，返回token
            return ApiJson.ok("登录成功", JWTUtil.sign(username, jwtConfigProperties));
        }else {
            return ApiJson.error("登录失败");
        }*/
        UserInfo user = new UserInfo();
        if (user != null){
            // 登录成功，返回token
            return ApiJson.ok("登录成功", JWTUtil.sign(username, jwtConfigProperties));
        }else {
            return ApiJson.error("登录失败");
        }
    }

    /**
     * 未登录，没有token会重定向到这个方法来
     * @param msg
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/unlogin")
    public ApiJson unlogin(String msg) throws UnsupportedEncodingException {
        return ApiJson.error(new String(msg.getBytes(), "UTF-8"));
    }

    /**
     * 获取所有用户，返回json数据，访问时需要在header中带上token（headers加上Authorization: token内容）
     * @return 返回json数据
     */
    @RequestMapping("/get/all")
    @ApiOperation(value = "查询所有用户")
    public ApiJson getAllUsers(){
        List<TestTable> result = tableService.getAll();
        return result != null ? ApiJson.ok(result) : ApiJson.error();
    }
}
