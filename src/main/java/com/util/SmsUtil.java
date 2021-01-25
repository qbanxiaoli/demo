package com.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.enums.TemplateCodeEnum;
import com.model.entity.Template;
import com.model.vo.SmsTemplateVO;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-26 01:17
 */
public class SmsUtil {

    private static final String url = "dysmsapi.aliyuncs.com";

    private static final String signName = "掌淘科技";

    private static final String accessKeyId = "";

    private static final String accessKeySecret = "";

    public static SmsTemplateVO sendSms(String phoneNumbers, String templateCode, Map<String, String> map) {
        CommonRequest request = new CommonRequest();
        request.setSysAction(TemplateCodeEnum.SendSms.name());
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(map));
        String result = SmsUtil.getCommonResponse(request);
        return JSON.parseObject(result, SmsTemplateVO.class);
    }

    public static SmsTemplateVO addSmsTemplate(Template template) {
        CommonRequest request = new CommonRequest();
        request.setSysAction(TemplateCodeEnum.AddSmsTemplate.name());
        SmsUtil.addParameter(template, request);
        String result = SmsUtil.getCommonResponse(request);
        return JSON.parseObject(result, SmsTemplateVO.class);
    }

    public static SmsTemplateVO deleteSmsTemplate(String templateCode) {
        CommonRequest request = new CommonRequest();
        request.setSysAction(TemplateCodeEnum.DeleteSmsTemplate.name());
        request.putQueryParameter("TemplateCode", templateCode);
        String result = SmsUtil.getCommonResponse(request);
        return JSON.parseObject(result, SmsTemplateVO.class);
    }

    public static SmsTemplateVO querySmsTemplate(String templateCode) {
        CommonRequest request = new CommonRequest();
        request.setSysAction(TemplateCodeEnum.QuerySmsTemplate.name());
        request.putQueryParameter("TemplateCode", templateCode);
        String result = SmsUtil.getCommonResponse(request);
        return JSON.parseObject(result, SmsTemplateVO.class);
    }

    public static SmsTemplateVO modifySmsTemplate(Template template) {
        CommonRequest request = new CommonRequest();
        request.setSysAction(TemplateCodeEnum.ModifySmsTemplate.name());
        SmsUtil.addParameter(template, request);
        request.putQueryParameter("TemplateCode", template.getTemplateCode());
        String result = SmsUtil.getCommonResponse(request);
        return JSON.parseObject(result, SmsTemplateVO.class);
    }

    private static void addParameter(Template template, CommonRequest request) {
        request.putQueryParameter("TemplateType", String.valueOf(template.getTemplateType()));
        request.putQueryParameter("TemplateName", template.getTemplateName());
        request.putQueryParameter("TemplateContent", template.getTemplateContent());
        request.putQueryParameter("Remark", template.getRemark());
    }

    @SneakyThrows
    private static String getCommonResponse(CommonRequest request) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(url);
        request.setSysVersion("2017-05-25");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        return client.getCommonResponse(request).getData();
    }

}