package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */
@Getter
@Setter
@Entity
@Table(name = "sys_dict", indexes = @Index(name = "idx_table_name_idx_field_name_idx_field_value", columnList = "tableName,fieldName,fieldValue", unique = true))
@ApiModel(value = "数据字典")
public class SysDict extends CreatorEntity {

    @Column(nullable = false, columnDefinition = "varchar(20) COMMENT '表名'")
    @ApiModelProperty(value = "表名", required = true)
    private String tableName;

    @Column(nullable = false, columnDefinition = "varchar(20) COMMENT '字段名'")
    @ApiModelProperty(value = "字段名", required = true)
    private String fieldName;

    @Column(nullable = false, columnDefinition = "int COMMENT '字段值'")
    @ApiModelProperty(value = "字段值", required = true)
    private Integer fieldValue;

    @Column(nullable = false, columnDefinition = "varchar(20) COMMENT '字段描述'")
    @ApiModelProperty(value = "字段描述", required = true)
    private String description;

}