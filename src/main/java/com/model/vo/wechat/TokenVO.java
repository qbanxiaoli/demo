package com.model.vo.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 11:25
 */
@Data
@ApiModel(value = "token返回模型模型")
public class TokenVO {

    @ApiModelProperty(value = "微信返回的access_token", required = true)
    private String accessToken;

    @ApiModelProperty(value = "过期时间(单位：s)", required = true)
    private Integer expiresIn;

}
