package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.EliteRatioQueryDTO;
import com.model.vo.*;
import com.service.ContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-24 13:22
 */
@RestController
@AllArgsConstructor
@Api(tags = "合约模块")
@RequestMapping("/contract")
public class ContractQueryController {

    private final ContractService contractService;

    @ApiOperation("精英账户多空持仓数对比—账户数")
    @PostMapping("/elite_account_ratio")
    public ResponseVO<ResultVO<ListVO<List<EliteAccountRatioVO>>>> getEliteAccountRatio(@ApiParam(name = "eliteRatioQueryDTO", value = "精英账户多空持仓数对比查询数据请求模型", required = true)
                                                                                        @Validated @RequestBody EliteRatioQueryDTO eliteRatioQueryDTO) {
        ResultVO<ListVO<List<EliteAccountRatioVO>>> eliteAccountRatio = contractService.getEliteAccountRatio(eliteRatioQueryDTO.getSymbol(), eliteRatioQueryDTO.getPeriod());
        return new ResponseVO<>(ResponseEnum.SUCCESS, eliteAccountRatio);
    }

    @ApiOperation("精英账户多空持仓数对比—持仓量")
    @PostMapping("/elite_position_ratio")
    public ResponseVO<ResultVO<ListVO<List<ElitePositionRatioVO>>>> getElitePositionRatio(@ApiParam(name = "eliteRatioQueryDTO", value = "精英账户多空持仓数对比查询数据请求模型", required = true)
                                                                                          @Validated @RequestBody EliteRatioQueryDTO eliteRatioQueryDTO) {
        ResultVO<ListVO<List<ElitePositionRatioVO>>> elitePositionRatio = contractService.getElitePositionRatio(eliteRatioQueryDTO.getSymbol(), eliteRatioQueryDTO.getPeriod());
        return new ResponseVO<>(ResponseEnum.SUCCESS, elitePositionRatio);
    }

}
