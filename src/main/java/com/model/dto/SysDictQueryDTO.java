package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */
@Getter
@Setter
@ApiModel("数据字典查询请求模型")
public class SysDictQueryDTO extends QueryDTO {

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "字段名")
    private String fieldName;

    @ApiModelProperty(value = "字段值")
    private Integer fieldValue;

    @ApiModelProperty(value = "字段描述")
    private String description;

}