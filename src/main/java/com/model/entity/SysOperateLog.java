package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */
@Getter
@Setter
@Entity
@Table(name = "sys_operate_log")
@ApiModel(value = "操作日志记录")
public class SysOperateLog extends SysLog {

    @Column(nullable = false, columnDefinition = "varchar(20) COMMENT '请求IP'")
    @ApiModelProperty(value = "请求IP", required = true)
    private String ipAddress;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '请求路径'")
    @ApiModelProperty(value = "请求路径", required = true)
    private String requestUrl;

    @Column(nullable = false, columnDefinition = "varchar(10) COMMENT '请求方式'")
    @ApiModelProperty(value = "请求方式", required = true)
    private String requestMethod;

    @Column(columnDefinition = "text COMMENT '返回结果'")
    @ApiModelProperty(value = "返回结果")
    private String responseBody;

    @Column(nullable = false, columnDefinition = "bigint COMMENT '请求耗时(单位：ms)'")
    @ApiModelProperty(value = "请求耗时(单位：ms)")
    private Long requestTime;

}