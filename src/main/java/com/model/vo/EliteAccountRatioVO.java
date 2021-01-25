package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-24 14:03
 */
@Data
@ApiModel(value = "精英账户多空持仓数对比-账户数返回模型")
public class EliteAccountRatioVO {

    @ApiModelProperty(value = "净多仓的账户比例", required = true)
    private String buyRatio;

    @ApiModelProperty(value = "净空仓的账户比例", required = true)
    private String sellRatio;

    @ApiModelProperty(value = "锁仓的账户比例", required = true)
    private String lockedRatio;

    @ApiModelProperty(value = "生成时间", required = true)
    private Date ts;

}
