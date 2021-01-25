package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Q版小李
 * @description
 * @create 2020/7/24 13:31
 */
@Getter
@Setter
@Entity
@Table(name = "sys_district")
@ApiModel(value = "省市区")
public class SysDistrict extends GmtEntity {

    @Column(columnDefinition = "bigint COMMENT '父级挂接id'")
    @ApiModelProperty(value = "父级挂接id")
    private Long pid;

    @Column(columnDefinition = "varchar(20) COMMENT '区划编码'")
    @ApiModelProperty(value = "区划编码")
    private String code;

    @Column(columnDefinition = "varchar(20) COMMENT '区划名称'")
    @ApiModelProperty(value = "区划名称")
    private String name;

    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(columnDefinition = "tinyint(1) COMMENT '状态(0->正常，-1->停用，-2->删除)'")
    @ApiModelProperty(value = "状态(0->正常，-1->停用，-2->删除)")
    private Integer status;

    @Column(columnDefinition = "tinyint(1) COMMENT '状态(0->自治区/直辖市，1->市级，2->县级)'")
    @ApiModelProperty(value = "状态(0->自治区/直辖市，1->市级，2->县级)")
    private Integer level;

}
