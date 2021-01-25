package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */
@Data
@ApiModel("动态数据字典查询请求模型")
public class SysDictDTO {

    @NotBlank
    @ApiModelProperty(value = "表名")
    private String tableName;

    @NotBlank
    @ApiModelProperty(value = "字段名")
    private String fieldName;

}