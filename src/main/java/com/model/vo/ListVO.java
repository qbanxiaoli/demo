package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-24 14:03
 */
@Data
@ApiModel(value = "列表返回模型")
public class ListVO<T> {

    @ApiModelProperty(value = "数据列表", required = true)
    private T list;


}
