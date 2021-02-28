package com.dxy.commerce.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 功能说明: 用户表
 *
 * @author dingxy
 * @date 2021/2/27 11:34 下午
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends Model {

    private static final long serialVersionUID = 989110L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 用户手机号
     */
    private String mobile;

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

    private String password;

}
