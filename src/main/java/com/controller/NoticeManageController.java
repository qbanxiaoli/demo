package com.controller;

import com.enums.ResponseEnum;
import com.model.vo.ResponseVO;
import com.model.vo.SmsTemplateVO;
import com.util.RandomUtil;
import com.util.SmsUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 23:15
 */
@RestController
@AllArgsConstructor
@Api(tags = "消息模块")
@RequestMapping("/notice")
public class NoticeManageController {

    @ApiOperation("添加第三方模版")
    @PostMapping("/template/add")
    public ResponseVO addTemplate() {
        return new ResponseVO<>(ResponseEnum.SUCCESS);
    }

    @ApiOperation("发送短信通知")
    @PostMapping("/sms/send/{phone}")
    public ResponseVO<SmsTemplateVO> sendSms(@ApiParam(name = "phone", value = "手机号码", required = true)
                                             @PathVariable(name = "phone") String phone) {
        Map<String, String> map = Maps.newHashMap();
        map.put("code", RandomUtil.generateString(6));
        SmsTemplateVO smsTemplateVO = SmsUtil.sendSms(phone, "SMS_140550680", map);
        return new ResponseVO<>(ResponseEnum.SUCCESS, smsTemplateVO);
    }

}
