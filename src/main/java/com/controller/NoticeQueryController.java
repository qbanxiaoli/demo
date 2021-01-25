package com.controller;

import com.enums.ResponseEnum;
import com.model.vo.ResponseVO;
import com.model.vo.SmsTemplateVO;
import com.util.SmsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-02-01 23:15
 */
@Slf4j
@RestController
@Api(tags = "消息模块")
@RequestMapping("/notice")
public class NoticeQueryController {

    @ApiOperation("查询阿里云短信模版")
    @PostMapping("/template/get/{templateCode}")
    public ResponseVO<SmsTemplateVO> queryTemplate(@ApiParam(name = "templateCode", value = "模版CODE", required = true)
                                                   @PathVariable(name = "templateCode") String templateCode) {
        SmsTemplateVO smsTemplateVO = SmsUtil.querySmsTemplate(templateCode);
        if (!smsTemplateVO.getCode().equals("OK")) {
            log.info("查询阿里云短信模版失败:{}", smsTemplateVO.getMessage());
            return new ResponseVO<>(ResponseEnum.FAILURE_VARIABLE, new Object[]{smsTemplateVO.getMessage()});
        }
        return new ResponseVO<>(ResponseEnum.SUCCESS, smsTemplateVO);
    }

}
