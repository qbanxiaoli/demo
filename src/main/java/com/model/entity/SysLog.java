package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */
@Getter
@Setter
@MappedSuperclass
@ApiModel(value = "日志模型")
public class SysLog extends CreatorEntity {

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '接口名称'")
    @ApiModelProperty(value = "接口名称", required = true)
    private String interfaceName;

    @Column(columnDefinition = "text COMMENT '请求入参'")
    @ApiModelProperty(value = "请求入参")
    private String requestBody;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '处理方法'")
    @ApiModelProperty(value = "处理方法", required = true)
    private String methodName;

}