package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-24 18:29
 */
@Data
@Builder
@ApiModel(value = "布林轨返回模型")
public class BrinTrackVO {

    @ApiModelProperty(value = "布林轨下轨", required = true)
    private BigDecimal lowerTrack;

    @ApiModelProperty(value = "布林轨中轨", required = true)
    private BigDecimal middleTrack;

    @ApiModelProperty(value = "布林轨上轨", required = true)
    private BigDecimal upperTrack;

}
