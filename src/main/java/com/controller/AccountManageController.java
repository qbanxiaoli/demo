package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.AccountFormDTO;
import com.model.vo.ResponseVO;
import com.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-16 09:36
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "账户模块")
@RequestMapping("/account")
public class AccountManageController {

    private final AccountService accountService;

    @ApiOperation("添加火币accessKeyId和accessKeySecret")
    @PostMapping("/add")
    public ResponseVO addAccount(@ApiParam(name = "accountFormDTO", value = "账户信息信息数据请求模型", required = true)
                                 @Validated @RequestBody AccountFormDTO accountFormDTO) {
        Long accountId = accountService.addAccount(accountFormDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, accountId);
    }

    @ApiOperation("更新火币accessKeyId和accessKeySecret")
    @PutMapping("/update")
    public ResponseVO updateAccount(@ApiParam(name = "accountFormDTO", value = "账户数据请求模型", required = true)
                                    @Validated @RequestBody AccountFormDTO accountFormDTO) {
        Long accountId = accountService.updateAccount(accountFormDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, accountId);
    }

}
