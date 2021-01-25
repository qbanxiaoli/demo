package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.KlineHistoryQueryDTO;
import com.model.dto.MarketDepthQueryDTO;
import com.model.vo.*;
import com.service.MarketService;
import com.util.BrinTrackUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-16 01:56
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "市场行情模块")
@RequestMapping("/market")
public class MarketQueryController {

    private final MarketService marketService;

    @ApiOperation("布林轨价格")
    @PostMapping("/brin_track")
    public ResponseVO<BrinTrackVO> getBrinTrack(@ApiParam(name = "klineHistoryQueryDTO", value = "K线查询数据传输模型", required = true)
                                   @Validated @RequestBody KlineHistoryQueryDTO klineHistoryQueryDTO) {
        BrinTrackVO brinTrackVO = BrinTrackUtil.getBrinTrack(klineHistoryQueryDTO.getSymbol(), klineHistoryQueryDTO.getPeriod());
        return new ResponseVO<>(ResponseEnum.SUCCESS, brinTrackVO);
    }

    @ApiOperation("K线数据(蜡烛图)")
    @PostMapping("/history/kline")
    public ResponseVO<ResultVO<List<KlineVO>>> getKlineHistory(@ApiParam(name = "klineHistoryQueryDTO", value = "K线查询数据传输模型", required = true)
                                                               @Validated @RequestBody KlineHistoryQueryDTO klineHistoryQueryDTO) {

        ResultVO<List<KlineVO>> klineHistory = marketService.getKlineHistory(klineHistoryQueryDTO.getSymbol(), klineHistoryQueryDTO.getPeriod(), klineHistoryQueryDTO.getSize());
        return new ResponseVO<>(ResponseEnum.SUCCESS, klineHistory);
    }

    @ApiOperation("聚合行情(Ticker)")
    @GetMapping("/detail/merged/{symbol}")
    public ResponseVO<ResultVO<MergedVO>> getMergedDetail(@ApiParam(name = "symbol", value = "交易对", example = "btcusdt", required = true)
                                                          @PathVariable(name = "symbol") String symbol) {
        ResultVO<MergedVO> mergedDetail = marketService.getMergedDetail(symbol);
        return new ResponseVO<>(ResponseEnum.SUCCESS, mergedDetail);
    }

    @ApiOperation("市场深度数据")
    @PostMapping("/depth")
    public ResponseVO<ResultVO<DepthVO>> getMarketDepth(@ApiParam(name = "marketDepthQueryDTO", value = "市场深度查询数据请求模型", required = true)
                                                        @Validated @RequestBody MarketDepthQueryDTO marketDepthQueryDTO) {

        ResultVO<DepthVO> marketDepth = marketService.getMarketDepth(marketDepthQueryDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, marketDepth);
    }

}
