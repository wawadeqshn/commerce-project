package com.dxy.commerce.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dxy.commerce.product.domain.TestTable;
import com.dxy.commerce.product.mapper.TestTableMapper;
import com.dxy.commerce.product.service.ITestTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 * @author dingxy
 * @date 2021/2/27 3:17 下午 
 * @return
 */
@Service
public class TestTableServiceImpl extends ServiceImpl<TestTableMapper, TestTable> implements ITestTableService {

    @Override
    public List<TestTable> getAll() {
        List<TestTable> resultList = this.baseMapper.selectList(new LambdaQueryWrapper<TestTable>());
        return resultList;
    }
}
