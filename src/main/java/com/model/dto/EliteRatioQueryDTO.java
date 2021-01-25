package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-24 14:03
 */
@Data
@ApiModel(value = "精英账户多空持仓数对比查询数据请求模型")
public class EliteRatioQueryDTO {

    @NotBlank
    @ApiModelProperty(value = "品种代码", required = true, example = "BTC")
    private String symbol;

    @NotBlank
    @ApiModelProperty(value = "周期", required = true, example = "1day")
    private String period;

}
