package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-18 16:38
 */
@Data
@ApiModel(value = "账户信息返回模型")
public class AccountVO {

    @ApiModelProperty(value = "账户id", required = true)
    private Long id;

    @ApiModelProperty(value = "账户状态(working：正常, lock：账户被锁定)", required = true)
    private String state;

    @ApiModelProperty(value = "账户类型(spot：现货账户， margin：逐仓杠杆账户，otc：OTC 账户，point：点卡账户，super-margin：全仓杠杆账户)", required = true)
    private String type;

    @ApiModelProperty(value = "子账户类型(仅对逐仓杠杆账户有效,逐仓杠杆交易标的，例如btcusdt)", required = true)
    private String subtype;

}
