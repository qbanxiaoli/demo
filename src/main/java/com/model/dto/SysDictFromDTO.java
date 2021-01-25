package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */
@Getter
@Setter
@ApiModel("字典数据请求模型")
public class SysDictFromDTO extends SysDictDTO {

    @NotNull
    @ApiModelProperty(value = "字段值", required = true)
    private Integer fieldValue;

    @NotBlank
    @ApiModelProperty(value = "字段描述", required = true)
    private String description;

}