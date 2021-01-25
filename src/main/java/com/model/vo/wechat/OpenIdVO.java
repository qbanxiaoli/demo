package com.model.vo.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 13:24
 */
@Data
@ApiModel(value = "微信openid数据返回模型")
public class OpenIdVO {

    @ApiModelProperty(value = "微信openid列表", required = true)
    private List<String> openid;

}
