package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-09 01:33
 */
@Data
@ApiModel("发送验证码数据请求模型")
public class CaptchaFormDTO {

    @NotBlank(message = "USERNAME_NOT_NULL")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotNull
    @ApiModelProperty(value = "验证码类型，默认发送邮箱验证码(1->邮箱验证码，2->短信验证码)", example = "1")
    private Integer type = 1;

}
