package com.controller;

import com.model.dto.EliteRatioQueryDTO;
import com.model.vo.ElitePositionRatioVO;
import com.model.vo.ListVO;
import com.model.vo.ResultVO;
import com.service.ContractService;
import com.util.FileUtil;
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
public class ContractManageController {

    private final ContractService contractService;

    @ApiOperation("导出精英账户多空持仓数对比—持仓量到csv文件")
    @PostMapping("/csv/export")
    public void exportElitePositionRatioToCSV(@ApiParam(name = "eliteRatioQueryDTO", value = "精英账户多空持仓数对比查询数据请求模型", required = true)
                                              @Validated @RequestBody EliteRatioQueryDTO eliteRatioQueryDTO) {
        ResultVO<ListVO<List<ElitePositionRatioVO>>> elitePositionRatio = contractService.getElitePositionRatio(eliteRatioQueryDTO.getSymbol(), eliteRatioQueryDTO.getPeriod());
        FileUtil.exportToCSV(elitePositionRatio.getData().getList(), ElitePositionRatioVO.class);
    }

}
