package com.model.vo.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 13:05
 */
@Getter
@Setter
@ApiModel(value = "微信用户列表返回模型")
public class UserListVO extends ErrorVO {

    @ApiModelProperty(value = "关注该公众账号的总用户数", required = true)
    private Integer total;

    @ApiModelProperty(value = "拉取的OPENID个数，最大值为10000", required = true)
    private Integer count;

    @ApiModelProperty(value = "列表数据，OPENID的列表", required = true)
    private OpenIdVO data;

    @ApiModelProperty(value = "拉取列表的最后一个用户的OPENID", required = true)
    private String nextOpenid;

}
