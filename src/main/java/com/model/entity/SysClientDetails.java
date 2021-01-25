package com.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/12/11 20:37
 */
@Data
@Entity
@Table(name = "oauth_client_details")
@ApiModel(value = "客户端详情")
public class SysClientDetails {

    @Id
    @Column(columnDefinition = "varchar(64) COMMENT '指定客户端标识'")
    @ApiModelProperty(value = "指定客户端标识", required = true)
    private String clientId;

    @Column(columnDefinition = "varchar(256) COMMENT '指定客户端所能访问的资源id集合'")
    @ApiModelProperty(value = "指定客户端所能访问的资源id集合")
    private String resourceIds;

    @Column(columnDefinition = "varchar(256) COMMENT '指定客户端的访问密匙'")
    @ApiModelProperty(value = "指定客户端的访问密匙", required = true)
    private String clientSecret;

    @Column(columnDefinition = "varchar(256) COMMENT '指定客户端申请的权限范围'")
    @ApiModelProperty(value = "指定客户端申请的权限范围", required = true)
    private String scope;

    @Column(columnDefinition = "varchar(256) COMMENT '指定客户端支持的授权模式'")
    @ApiModelProperty(value = "指定客户端支持的授权模式", required = true)
    private String authorizedGrantTypes;

    @Column(columnDefinition = "varchar(256) COMMENT '客户端的重定向URI,可为空'")
    @ApiModelProperty(value = "客户端的重定向URI,可为空")
    private String webServerRedirectUri;

    @Column(columnDefinition = "varchar(256) COMMENT '指定客户端所拥有的Spring Security的权限值'")
    @ApiModelProperty(value = "指定客户端所拥有的Spring Security的权限值")
    private String authorities;

    @Column(columnDefinition = "int COMMENT '设定客户端的access_token的有效时间值(单位:秒),可选'")
    @ApiModelProperty(value = "设定客户端的access_token的有效时间值(单位:秒),可选")
    private String accessTokenValidity;

    @Column(columnDefinition = "int COMMENT '设定客户端的refresh_token的有效时间值(单位:秒),可选'")
    @ApiModelProperty(value = "设定客户端的refresh_token的有效时间值(单位:秒),可选")
    private String refreshTokenValidity;

    @Column(columnDefinition = "varchar(4096) COMMENT '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选'")
    @ApiModelProperty(value = "这是一个预留的字段,在Oauth的流程中没有实际的使用,可选")
    private String additionalInformation;

    @Column(columnDefinition = "varchar(256) COMMENT '设置用户是否自动Approval操作, 默认值为false'")
    @ApiModelProperty(value = "设置用户是否自动Approval操作, 默认值为false")
    private String autoapprove;

}
