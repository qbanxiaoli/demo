package com.service;

import com.model.dto.MarketDepthQueryDTO;
import com.model.vo.DepthVO;
import com.model.vo.KlineVO;
import com.model.vo.MergedVO;
import com.model.vo.ResultVO;

import java.util.List;

/**
 * @author Q版小李
 * @description 市场行情
 * @create 2019/1/18 15:58
 */
public interface MarketService {

    // 查询k线历史
    ResultVO<List<KlineVO>> getKlineHistory(String symbol, String period, Integer size);

    // 查询聚合行情
    ResultVO<MergedVO> getMergedDetail(String symbol);

    // 查询市场深度
    ResultVO<DepthVO> getMarketDepth(MarketDepthQueryDTO marketDepthQueryDTO);

}
