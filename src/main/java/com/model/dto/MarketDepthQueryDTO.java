package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-16 03:10
 */
@Data
@ApiModel(value = "市场深度查询数据请求模型")
public class MarketDepthQueryDTO {

    @NotBlank(message = "SYMBOL_NOT_NULL")
    @ApiModelProperty(value = "交易对", required = true, example = "btcusdt")
    private String symbol;

    @NotNull
    @ApiModelProperty(value = "返回深度的数量", required = true, example = "20")
    private Integer depth;

    @ApiModelProperty(value = "深度的价格聚合度", example = "step0")
    private String type;

}
