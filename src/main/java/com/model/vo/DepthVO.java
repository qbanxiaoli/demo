package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author qbanxiaoli
 * @description
 * @create 2020-04-07 09:51
 */
@Data
@ApiModel(value = "市场深度数据返回模型")
public class DepthVO {

    @ApiModelProperty(value = "行情id", required = true)
    private Long id;

    @ApiModelProperty(value = "内部字段", required = true)
    private String version;

    @ApiModelProperty(value = "当前时间", required = true)
    private Date ts;

    @ApiModelProperty(value = "当前的所有买单 [price, quote volume]", required = true)
    private List<List<BigDecimal>> bids;

    @ApiModelProperty(value = "当前的所有卖单 [price, quote volume]", required = true)
    private List<List<BigDecimal>> asks;

}
