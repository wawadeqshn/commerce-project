package com.dxy.commerce.product.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/27 11:40 下午
 */
@Data
public class UserDTO {

    private static final long serialVersionUID = 11L;

    private Long id;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 数据库密码salt
     */
    private String dbSalt;

    /**
     * token的salt
     */
    private String tokenSalt;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifiedTime;

    private String encryptPwd;
}
