package com.dxy.commerce.product.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/3/15 10:40 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonVo implements Serializable {
    private String id;
    private String name;
}
