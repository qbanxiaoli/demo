package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-15 18:21
 */
@Data
@ApiModel(value = "jwt公钥模型")
public class TokenKeyVO {

    @ApiModelProperty(value = "加密算法", required = true)
    private String alg;

    @ApiModelProperty(value = "公钥", required = true)
    private String value;

}
