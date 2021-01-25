package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:01 PM
 */
@Getter
@Setter
@DynamicUpdate
@Entity
@Table(name = "sys_file_info", indexes = @Index(name = "idx_file_name", columnList = "fileName"))
@ApiModel(value = "文件存储信息")
public class SysFileInfo extends CreatorEntity {

    @Column(nullable = false, columnDefinition = "varchar(50) COMMENT '文件服务器访问地址'")
    @ApiModelProperty(value = "文件服务器访问地址", required = true)
    private String webServerUrl;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '文件路径'")
    @ApiModelProperty(value = "文件路径", required = true)
    private String storePath;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '文件类型'")
    @ApiModelProperty(value = "文件类型", required = true)
    private String contentType;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '文件名'")
    @ApiModelProperty(value = "文件名", required = true)
    private String fileName;

    @Column(nullable = false, columnDefinition = "varchar(10) COMMENT '文件扩展名'")
    @ApiModelProperty(value = "文件扩展名", required = true)
    private String fileExtension;

    @Column(nullable = false, columnDefinition = "bigint COMMENT '文件大小'")
    @ApiModelProperty(value = "文件大小", required = true)
    private Long fileSize;

    @Column(nullable = false, columnDefinition = "tinyint(1) COMMENT '是否压缩(1->是，0->否)'")
    @ApiModelProperty(value = "是否压缩(true->是，false->否)", required = true)
    private Boolean compression;

}

