package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-03-30 13:35
 */
@Data
@Builder
@ApiModel(value = "文件地址返回模型")
public class SysFileInfoVO {

    @ApiModelProperty(value = "文件id", required = true)
    private Long id;

    @ApiModelProperty(value = "文件访问地址", required = true)
    private String fileUrl;

}
