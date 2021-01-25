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
@ApiModel(value = "精英账户多空持仓数对比-持仓量返回模型")
public class ElitePositionRatioVO {

    @ApiModelProperty(value = "多仓的总持仓量占比", required = true)
    private String buyRatio;

    @ApiModelProperty(value = "空仓的总持仓量占比", required = true)
    private String sellRatio;

    @ApiModelProperty(value = "生成时间", required = true)
    private Date ts;

}
