package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;


/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */
@Data
@ApiModel("动态数据字典查询返回模型")
public class SysDictVO {

    @ApiModelProperty(value = "字段值")
    private Integer fieldValue;

    @ApiModelProperty(value = "字段描述")
    private String description;

}