package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/7/3 13:31
 */
@Data
@ApiModel(value = "统一返回模型")
public class ResultVO<T> {

    @ApiModelProperty(value = "请求处理结果", required = true)
    private String status;

    @ApiModelProperty(value = "错误信息", required = true)
    private String errMsg;

    @ApiModelProperty(value = "错误码", required = true)
    private String errCode;

    @ApiModelProperty(value = "响应生成时间点，单位：毫秒", required = true)
    private Date ts;

    @ApiModelProperty(value = "数据所属的 channel，格式： market.$symbol.kline.$period", required = true)
    private String ch;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "返回数据")
    private T tick;

}