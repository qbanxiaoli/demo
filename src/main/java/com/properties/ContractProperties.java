package com.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-24 13:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "contract")
public class ContractProperties {

    @ApiModelProperty(value = "精英账户多空持仓数对比—账户数", required = true)
    private String eliteAccountRatio;

    @ApiModelProperty(value = "精英账户多空持仓数对比—持仓量", required = true)
    private String elitePositionRatio;

}
