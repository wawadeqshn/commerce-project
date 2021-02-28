package com.dxy.commerce.product.service.account;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dxy.commerce.product.domain.UserInfo;
import com.dxy.commerce.product.dto.UserDTO;
import com.dxy.commerce.product.exception.BusinessException;

/**
 * 功能说明: 用户表 服务类
 *
 * @author dingxy
 * @date 2021/2/27 10:47 下午
 */
public interface IUserService extends IService<UserInfo> {

    /**
     * 保存user登录信息，返回token
     *
     * @param account
     * @return
     */
    String generateJwtToken(String account);

    /**
     * 获取上次token生成时的salt值和登录用户信息
     *
     * @param account
     * @return
     */
    UserDTO getJwtTokenInfo(String account);

    /**
     * 获取用户信息
     *
     * @param account
     * @return
     */
    UserDTO getUserInfo(String account);

    /**
     * 清除token信息
     * @param account
     * @param openId
     * @throws BusinessException
     */
    void deleteLoginInfo(String account, String openId) throws BusinessException;

}
