package com.controller;

import com.enums.ResponseEnum;
import com.model.vo.AccountVO;
import com.model.vo.ResponseVO;
import com.model.vo.ResultVO;
import com.service.AccountService;
import com.model.entity.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-16 09:36
 */
@RestController
@AllArgsConstructor
@Api(tags = "账户模块")
@RequestMapping("/account")
public class AccountQueryController {

    private final AccountService accountService;

    @ApiOperation("查询火币accessKeyId和accessKeySecret")
    @GetMapping()
    public ResponseVO getAccount() {
        Account account = accountService.getAccount();
        return new ResponseVO<>(ResponseEnum.SUCCESS, account);
    }

    @ApiOperation("查询账户信息")
    @GetMapping("/accounts")
    public ResponseVO queryAccountInformation() {
        ResultVO<List<AccountVO>> accountInformation = accountService.getAccountInformation();
        if (!accountInformation.getStatus().equals("ok")) {
            return new ResponseVO<>(ResponseEnum.FAILURE, accountInformation);
        }
        return new ResponseVO<>(ResponseEnum.SUCCESS, accountInformation);
    }

}
