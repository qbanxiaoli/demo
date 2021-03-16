package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/7/3 13:54
 */
@Getter
@Setter
@ToString
@ApiModel(value = "分页查询请求模型")
public class QueryDTO {

    @Min(value = 1)
    @ApiModelProperty(value = "当前页", example = "1")
    private Integer currentPage = 1;

    @Min(value = 10)
    @ApiModelProperty(value = "每页记录数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "是否按降序排列，默认降序排列(false->升序排列，true->降序排列)", example = "true")
    private Boolean order = true;

}
