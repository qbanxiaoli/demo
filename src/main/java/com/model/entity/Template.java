package com.model.entity;

import com.enums.NoticeTemplateStatusEnum;
import com.enums.NoticeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:33
 */

@Getter
@Setter
@Entity
@Table(name = "template")
@ApiModel(value = "第三方模版")
public class Template extends CreatorEntity {

    @Column(columnDefinition = "varchar(15) COMMENT '短信模板CODE'")
    @ApiModelProperty(value = "短信模板CODE", required = true)
    private String templateCode;

    @Column(nullable = false, columnDefinition = "varchar(30) COMMENT '模版名称'")
    @ApiModelProperty(value = "模版名称", required = true)
    private String templateName;

    @Column(nullable = false, columnDefinition = "varchar(256) COMMENT '模版内容'")
    @ApiModelProperty(value = "模版内容", required = true)
    private String templateContent;

    @Column(columnDefinition = "tinyint(1) COMMENT '短信类型(0->验证码,1->短信通知,2->推广短信,3->国际/港澳台消息)'")
    @ApiModelProperty(value = "短信类型(0->验证码,1->短信通知,2->推广短信,3->国际/港澳台消息)")
    private Integer templateType;

    @Column(nullable = false, columnDefinition = "tinyint(1) COMMENT '业务类型(0->注册，1->登录，2->修改密码)'")
    @ApiModelProperty(value = "业务类型(0->注册，1->登录，2->修改密码)", required = true)
    private Integer type;

    @Column(nullable = false, columnDefinition = "tinyint(1) COMMENT '消息渠道(1->短信，2->邮件)'")
    @ApiModelProperty(value = "消息渠道(1->短信，2->邮件)", required = true)
    private NoticeTypeEnum noticeType;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '0' COMMENT '模板状态(0->草稿，1->待审核，2->审核未通过,3->启用，4->禁用)'")
    @ApiModelProperty(value = "模板状态(0->草稿，1->待审核，2->审核未通过,3->启用，4->禁用)", required = true)
    private Integer noticeTemplateStatus = NoticeTemplateStatusEnum.DRAFT.getValue();

    @Column(columnDefinition = "varchar(256) COMMENT '备注'")
    @ApiModelProperty(value = "备注")
    private String remark;

}