package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-01-04 17:11
 */
@Data
@ApiModel("用户登录数据请求模型")
public class LoginFormDTO {

    @NotBlank(message = "USERNAME_NOT_NULL")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank(message = "PASSWORD_NOT_NULL")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
