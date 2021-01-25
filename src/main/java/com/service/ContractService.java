package com.service;

import com.model.vo.EliteAccountRatioVO;
import com.model.vo.ElitePositionRatioVO;
import com.model.vo.ListVO;
import com.model.vo.ResultVO;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-24 13:24
 */
public interface ContractService {

    // 查询精英账户多空持仓数对比—账户数
    ResultVO<ListVO<List<EliteAccountRatioVO>>> getEliteAccountRatio(String symbol, String period);

    // 查询精英账户多空持仓数对比—持仓量
    ResultVO<ListVO<List<ElitePositionRatioVO>>> getElitePositionRatio(String symbol, String period);

}
