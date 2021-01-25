package com.job;

import com.enums.SymbolEnum;
import com.enums.TimeEnum;
import com.enums.TrendEnum;
import com.model.converter.MarketInfoAssembly;
import com.model.entity.MarketInfo;
import com.model.vo.BrinTrackVO;
import com.model.vo.KlineVO;
import com.model.vo.ResultVO;
import com.service.MarketService;
import com.util.BrinTrackUtil;
import com.dao.repository.MarketInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description 同步更新K线趋势
 * @create 2020-05-27 11:26
 */
@Slf4j
@Component
@AllArgsConstructor
public class MarketInfoJob {

    private final MarketService marketService;

    private final MarketInfoRepository marketInfoRepository;

    @Scheduled(cron = "0 * * * * ?")
    public void execute() {
        this.updateMarketInfo(SymbolEnum.BTC_USDT.getValue(), TimeEnum.FIFTEEN_MINUTE.getValue());
        this.updateMarketInfo(SymbolEnum.BTC_USDT.getValue(), TimeEnum.SIXTY_MINUTE.getValue());
        this.updateMarketInfo(SymbolEnum.BTC_USDT.getValue(), TimeEnum.FOUR_HOUR.getValue());
    }

    // 同步更新K线趋势
    private void updateMarketInfo(String symbol, String period) {
        // 查询最近两条K线
        ResultVO<List<KlineVO>> klineHistory = marketService.getKlineHistory(symbol, period, 2);
        if (klineHistory.getData().isEmpty()) {
            return;
        }
        // 获取最近一条已收盘K线
        KlineVO klineVO = klineHistory.getData().get(1);
        // 查询当前K线布林轨价格
        BrinTrackVO brinTrackVO = BrinTrackUtil.getBrinTrack(symbol, period);
        // 查询数据库K线同步记录
        MarketInfo marketInfo = marketInfoRepository.findByPeriod(period);
        if (marketInfo == null) {
            // 第一次同步K线趋势未知
            marketInfo = MarketInfoAssembly.toDomain(brinTrackVO, klineVO);
            marketInfo.setPeriod(period);
            marketInfoRepository.save(marketInfo);
        }
        if (!marketInfo.getKlineId().equals(klineVO.getId())) {
            // 如果K线是上升趋势
            if (marketInfo.getTrend().equals(TrendEnum.UPWARD.getValue())) {
                // K线最低价处于布林轨下轨外且K线收阴,则趋势逆转
                if (klineVO.getLow().compareTo(brinTrackVO.getLowerTrack()) < 0 && klineVO.getOpen().compareTo(klineVO.getClose()) > 0) {
                    marketInfo.setTrend(TrendEnum.DOWNWARD.getValue());
                }
                // K线向下穿过布林轨中轨,则趋势逆转
                if (klineVO.getOpen().compareTo(brinTrackVO.getMiddleTrack()) > 0 && klineVO.getClose().compareTo(brinTrackVO.getMiddleTrack()) < 0) {
                    marketInfo.setTrend(TrendEnum.DOWNWARD.getValue());
                }
                // K线最高价处于布林轨上轨外且K线收阴,则趋势逆转
                if (klineVO.getHigh().compareTo(brinTrackVO.getUpperTrack()) > 0 && klineVO.getOpen().compareTo(klineVO.getClose()) > 0) {
                    marketInfo.setTrend(TrendEnum.DOWNWARD.getValue());
                }
                MarketInfo newMarketInfo = MarketInfoAssembly.toDomain(marketInfo, brinTrackVO, klineVO);
                marketInfoRepository.save(newMarketInfo);
            }
            // 如果K线是下降趋势
            if (marketInfo.getTrend().equals(TrendEnum.DOWNWARD.getValue())) {
                // K线最高价处于布林轨上轨外且K线收阳,则趋势逆转
                if (klineVO.getHigh().compareTo(brinTrackVO.getUpperTrack()) > 0 && klineVO.getOpen().compareTo(klineVO.getClose()) < 0) {
                    marketInfo.setTrend(TrendEnum.UPWARD.getValue());
                }
                // K线向上穿过布林轨中轨,则趋势逆转
                if (klineVO.getOpen().compareTo(brinTrackVO.getMiddleTrack()) < 0 && klineVO.getClose().compareTo(brinTrackVO.getMiddleTrack()) > 0) {
                    marketInfo.setTrend(TrendEnum.UPWARD.getValue());
                }
                // K线最低价处于布林轨下轨外且K线收阳,则趋势逆转
                if (klineVO.getLow().compareTo(brinTrackVO.getLowerTrack()) < 0 && klineVO.getOpen().compareTo(klineVO.getClose()) < 0) {
                    marketInfo.setTrend(TrendEnum.UPWARD.getValue());
                }
                MarketInfo newMarketInfo = MarketInfoAssembly.toDomain(marketInfo, brinTrackVO, klineVO);
                marketInfoRepository.save(newMarketInfo);
            }
            // 如果K线趋势未知
            if (marketInfo.getTrend().equals(TrendEnum.UN_KNOW.getValue())) {
                // K线最低价处于布林轨下轨外且K线收阳,则趋势确定为超跌反弹
                if (klineVO.getLow().compareTo(brinTrackVO.getLowerTrack()) < 0 && klineVO.getOpen().compareTo(klineVO.getClose()) < 0) {
                    marketInfo.setTrend(TrendEnum.UPWARD.getValue());
                }
                // K线最高价处于布林轨上轨外且K线收阴,则趋势确定为触顶回落
                if (klineVO.getHigh().compareTo(brinTrackVO.getUpperTrack()) > 0 && klineVO.getOpen().compareTo(klineVO.getClose()) > 0) {
                    marketInfo.setTrend(TrendEnum.DOWNWARD.getValue());
                }
                // K线向下穿过布林轨中轨,则趋势确定为向下
                if (klineVO.getOpen().compareTo(brinTrackVO.getMiddleTrack()) > 0 && klineVO.getClose().compareTo(brinTrackVO.getMiddleTrack()) < 0) {
                    marketInfo.setTrend(TrendEnum.DOWNWARD.getValue());
                }
                // K线向上穿过布林轨中轨,则趋势确定为向上
                if (klineVO.getOpen().compareTo(brinTrackVO.getMiddleTrack()) < 0 && klineVO.getClose().compareTo(brinTrackVO.getMiddleTrack()) > 0) {
                    marketInfo.setTrend(TrendEnum.UPWARD.getValue());
                }
                MarketInfo newMarketInfo = MarketInfoAssembly.toDomain(marketInfo, brinTrackVO, klineVO);
                marketInfoRepository.save(newMarketInfo);
            }
        }
    }

}
