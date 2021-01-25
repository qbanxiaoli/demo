package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description 自定义分页器
 * @create 2019-11-15 19:49
 */
@Data
@Builder
@ApiModel(value = "分页模型")
public class PageVO<T> {

    @ApiModelProperty(value = "当前页", required = true)
    private Integer currentPage;

    @ApiModelProperty(value = "页面大小", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "总条数", required = true)
    private Long total;

    @ApiModelProperty(value = "分页结果", required = true)
    private List<T> results;

}