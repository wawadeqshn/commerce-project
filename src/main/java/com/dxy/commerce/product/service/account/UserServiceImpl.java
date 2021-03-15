package com.dxy.commerce.product.service.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxy.commerce.product.constants.SystemConstant;
import com.dxy.commerce.product.domain.UserInfo;
import com.dxy.commerce.product.domain.cache.RedisService;
import com.dxy.commerce.product.dto.UserDTO;
import com.dxy.commerce.product.exception.BusinessException;
import com.dxy.commerce.product.mapper.UserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/27 10:48 下午
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements IUserService {

    @Autowired
    private RedisService redisService;

    @Override
    public String generateJwtToken(String account) {
        return "---";
        //return JwtUtils.sign(account, this.getTokenSaltFromDB(account), SystemConstant.TOKEN_EXPIRES_SECONDS);
    }

    @Override
    public UserDTO getJwtTokenInfo(String account) {
        UserDTO userInfo = getUserInfo(account);
        // 这儿缓存了tokenSalt所以这样取
        userInfo.setTokenSalt(this.getTokenSaltFromDB(account));
        return userInfo;
    }

    /**
     * 获取账号信息
     *
     * @param account
     * @return
     */
    @Override
    @Transactional
    public UserDTO getUserInfo(String account) {

        // UserDTO cacheDto =
        // (UserDTO) redisService.getRedisTemplate().opsForValue().get(SystemConstant.REDIS_KEY_DTO_PROPRIETOR +
        // account);
        //
        // if (Objects.isNull(cacheDto)) {

        UserInfo userInfo = getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getAccount, account));
        if (Objects.isNull(userInfo)) {
            return null;
        }
        UserDTO niceUserDTO = new UserDTO();

        niceUserDTO.setName(userInfo.getName());
        niceUserDTO.setId(userInfo.getId());
        niceUserDTO.setDbSalt(userInfo.getDbSalt());
        // 设置加密密码
        niceUserDTO.setEncryptPwd(userInfo.getPassword());
        niceUserDTO.setAccount(userInfo.getAccount());

        return niceUserDTO;
    }

    @Override
    public void deleteLoginInfo(String account, String openId) throws BusinessException {

    }

    public String getTokenSaltFromDB(String account) {
        String cacheToken = redisService.getStringRedisTemplate().opsForValue().get(SystemConstant.REDIS_KEY_TOKEN_SALT + account);
        if (Strings.isEmpty(cacheToken)) {
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserInfo::getAccount, account);
            UserInfo userInfo = this.getOne(queryWrapper);
            String token = userInfo.getTokenSalt();
            redisService.getStringRedisTemplate().opsForValue().set(SystemConstant.REDIS_KEY_TOKEN_SALT + account,
                    token, SystemConstant.TTL_DAYS, TimeUnit.DAYS);
            return token;
        } else {
            return cacheToken;
        }

    }

}
