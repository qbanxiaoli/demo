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
@Table(name = "account", indexes = @Index(name = "idx_user_id", columnList = "userId", unique = true))
@ApiModel(value = "账户信息")
public class Account extends CreatorEntity {

    @Column(nullable = false, columnDefinition = "bigint COMMENT '用户id'")
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    @Column(nullable = false, columnDefinition = "varchar(50) COMMENT '火币accessKeyId'")
    @ApiModelProperty(value = "火币accessKeyId", required = true)
    private String accessKeyId;

    @Column(nullable = false, columnDefinition = "varchar(50) COMMENT '火币accessKeySecret'")
    @ApiModelProperty(value = "火币accessKeySecret", required = true)
    private String accessKeySecret;

}

