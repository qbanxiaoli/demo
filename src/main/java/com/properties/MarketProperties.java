package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Q版小李
 * @description
 * @create 2019/1/18 17:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "market")
public class MarketProperties {

    @ApiModelProperty(value = "K线查询地址", required = true)
    private String kline;

    @ApiModelProperty(value = "滚动24小时交易和最优报价聚合行情(单个symbol)", required = true)
    private String merged;

    @ApiModelProperty(value = "全部symbol的交易行情", required = true)
    private String tickers;

    @ApiModelProperty(value = "市场深度行情（单个symbol）", required = true)
    private String depth;

    @ApiModelProperty(value = "单个symbol最新成交记录", required = true)
    private String trade;

    @ApiModelProperty(value = "单个symbol批量成交记录", required = true)
    private String tradeHistory;

    @ApiModelProperty(value = "滚动24小时交易聚合行情(单个symbol)", required = true)
    private String detail;

}
