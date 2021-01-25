package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-16 03:10
 */
@Data
@ApiModel(value = "K线查询数据请求模型")
public class KlineHistoryQueryDTO {

    @NotBlank(message = "SYMBOL_NOT_NULL")
    @ApiModelProperty(value = "交易对", required = true, example = "btcusdt")
    private String symbol;

    @NotBlank(message = "PERIOD_NOT_NULL")
    @ApiModelProperty(value = "返回数据时间粒度，也就是每根蜡烛的时间区间", required = true, example = "1min")
    private String period;

    @Range(min = 1, max = 2000)
    @ApiModelProperty(value = "返回K线数据条数[1, 2000]", example = "15")
    private Integer size = 15;

}
