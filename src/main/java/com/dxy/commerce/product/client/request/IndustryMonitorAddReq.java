package com.dxy.commerce.product.client.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 功能说明:
 *
 * @author dingxy
 * @date 2021/2/27 4:25 下午
 */
@ApiModel
@Data
public class IndustryMonitorAddReq {

    @ApiModelProperty(value = "监控类型")
    @NotBlank(message = "监控类型不能为空")
    private String monitor_type;

    @ApiModelProperty(value = "报表模版类型,主要有010（通用）、070（银行）、130（券商）、190（保险），绝大多数是010。暂时先用 010")
    @NotBlank(message = "报表模版类型不能为空")
    private String model_class;

    @ApiModelProperty(value = "操作用户的id", example = "1")
    private Integer uid;
}
