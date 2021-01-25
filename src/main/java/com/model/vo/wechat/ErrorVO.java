package com.model.vo.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-28 13:39
 */
@Getter
@Setter
@ApiModel(value = "错误信息返回模型")
public class ErrorVO {

    @ApiModelProperty(value = "错误码")
    private Integer errcode;

    @ApiModelProperty(value = "错误信息")
    private String errmsg;

}
