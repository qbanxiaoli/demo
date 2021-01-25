package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:01 PM
 */
@Data
@ApiModel(value = "账户信息数据请求模型")
public class AccountFormDTO {

    @NotBlank
    @ApiModelProperty(value = "火币accessKeyId", required = true)
    private String accessKeyId;

    @NotBlank
    @ApiModelProperty(value = "火币accessKeySecret", required = true)
    private String accessKeySecret;

}

