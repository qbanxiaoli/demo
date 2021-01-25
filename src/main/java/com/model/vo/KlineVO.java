package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Q版小李
 * @description
 * @create 2019/1/18 18:54
 */
@Data
@ApiModel(value = "K线数据返回模型")
public class KlineVO {

    @ApiModelProperty(value = "调整为新加坡时间的时间戳，单位秒，并以此作为此K线柱的id", required = true)
    private Long id;

    @ApiModelProperty(value = "以基础币种计量的交易量", required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "交易次数", required = true)
    private Integer count;

    @ApiModelProperty(value = "本阶段开盘价", required = true)
    private BigDecimal open;

    @ApiModelProperty(value = "本阶段收盘价", required = true)
    private BigDecimal close;

    @ApiModelProperty(value = "本阶段最低价", required = true)
    private BigDecimal low;

    @ApiModelProperty(value = "本阶段最高价", required = true)
    private BigDecimal high;

    @ApiModelProperty(value = "以报价币种计量的交易量", required = true)
    private BigDecimal vol;

}
