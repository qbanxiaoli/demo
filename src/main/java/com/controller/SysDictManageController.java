package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.SysDictFromDTO;
import com.model.vo.ResponseVO;
import com.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-15 11:38
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "数据字典模块")
@RequestMapping("/sys_dict")
public class SysDictManageController {

    private final SysDictService sysDictService;

    @ApiOperation("添加数据字典")
    @PostMapping("/add")
    public ResponseVO addSysDict(@ApiParam(name = "sysDictFromDTO", value = "字典数据请求模型", required = true)
                                 @Validated @RequestBody SysDictFromDTO sysDictFromDTO) {
        sysDictService.addSysDict(sysDictFromDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS);
    }

}
