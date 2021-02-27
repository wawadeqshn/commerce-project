package com.dxy.commerce.product.service;

import com.dxy.commerce.product.domain.TestTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *服务类
 * @author dingxy
 * @date 2021/2/27 3:16 下午 
 * @return 
 */
public interface ITestTableService extends IService<TestTable> {

    List<TestTable> getAll();
}
