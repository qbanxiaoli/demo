package com.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 22:30
 */
@Data
@ApiModel(value = "短信模版查询返回模型")
public class SmsTemplateVO {

    @ApiModelProperty(value = "请求状态码", required = true)
    private String code;

    @ApiModelProperty(value = "短信模板的创建日期和时间", required = true)
    private String createDate;

    @ApiModelProperty(value = "状态码的描述", required = true)
    private String message;

    @ApiModelProperty(value = "审核备注", required = true)
    private String reason;

    @ApiModelProperty(value = "请求ID", required = true)
    private String requestId;

    @ApiModelProperty(value = "短信模板CODE", required = true)
    private String templateCode;

    @ApiModelProperty(value = "模板内容", required = true)
    private String templateContent;

    @ApiModelProperty(value = "模板名称", required = true)
    private String templateName;

    @ApiModelProperty(value = "模板审核状态(0->审核中,1->审核通过,2->审核失败，请在返回参数reason中查看审核失败原因)", required = true)
    private Integer templateStatus;

    @ApiModelProperty(value = "短信类型(0->验证码,1->短信通知,2->推广短信,3->国际/港澳台消息)", required = true)
    private Integer templateType;

}