package com.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author terminus
 * @description
 * @create 2019/11/25 2:20 下午
 */
@Data
@ApiModel(value = "图片上传数据请求模型")
public class SysFileInfoFromDTO {

    @NotNull
    @ApiModelProperty(value = "是否上传缩略图，默认上传原图(false->否，true->是)")
    private Boolean thumbnail = false;

    @ApiModelProperty(value = "缩略图尺寸，宽度", example = "150")
    private Integer width = 150;

    @ApiModelProperty(value = "缩略图尺寸，高度", example = "150")
    private Integer height = 150;

}
