package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-26 01:16
 */
@Getter
@AllArgsConstructor
public enum TemplateCodeEnum {

    SendSms("发送短信"),
    AddSmsTemplate("申请短信模板"),
    DeleteSmsTemplate("删除短信模板"),
    ModifySmsTemplate("修改未通过审核的短信模板"),
    QuerySmsTemplate("查询短信模板的审核状态");

    private final String description;

}