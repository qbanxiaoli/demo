package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author terminus
 * @description
 * @create 2019/10/18 10:48
 */
@Data
@ApiModel(value = "聚合行情数据返回模型")
public class MergedVO {

    @ApiModelProperty(value = "行情id", required = true)
    private Long id;

    @ApiModelProperty(value = "以基础币种计量的交易量", required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "交易次数", required = true)
    private Integer count;

    @ApiModelProperty(value = "本阶段开盘价", required = true)
    private BigDecimal open;

    @ApiModelProperty(value = "本阶段最新价", required = true)
    private BigDecimal close;

    @ApiModelProperty(value = "本阶段最低价", required = true)
    private BigDecimal low;

    @ApiModelProperty(value = "本阶段最高价", required = true)
    private BigDecimal high;

    @ApiModelProperty(value = "以报价币种计量的交易量", required = true)
    private BigDecimal vol;

    @ApiModelProperty(value = "当前的最高买价 [price, quote volume]", required = true)
    private List<BigDecimal> bid;

    @ApiModelProperty(value = "当前的最低卖价 [price, quote volume]", required = true)
    private List<BigDecimal> ask;

}
