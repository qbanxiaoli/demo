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
@Table(name = "sys_error_log")
@ApiModel(value = "异常日志记录")
public class SysErrorLog extends SysLog {

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '异常类名'")
    @ApiModelProperty(value = "异常类名", required = true)
    private String className;

    @Column(columnDefinition = "text COMMENT '错误信息'")
    @ApiModelProperty(value = "错误信息", required = true)
    private String message;

    @Column(nullable = false, columnDefinition = "text COMMENT '堆栈错误'")
    @ApiModelProperty(value = "堆栈错误", required = true)
    private String error;

}