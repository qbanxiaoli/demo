package com.job;

import com.enums.OperateTypeEnum;
import com.enums.SymbolEnum;
import com.enums.TimeEnum;
import com.enums.TrendEnum;
import com.model.entity.MarketInfo;
import com.model.vo.BrinTrackVO;
import com.model.vo.KlineVO;
import com.model.vo.ResultVO;
import com.service.MarketService;
import com.util.BrinTrackUtil;
import com.util.JsonUtil;
import com.strategy.util.OperateUtil;
import com.dao.repository.MarketInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description 自动交易
 * @create 2020-01-04 11:09
 */
@Slf4j
@Component
@AllArgsConstructor
public class QuantizationJob {

    private final MarketService marketService;

    private final MarketInfoRepository marketInfoRepository;

    @Scheduled(fixedRate = 3000)
    public void execute() {
        // 查询最近两条4小时K线
        ResultVO<List<KlineVO>> klineHistory = marketService.getKlineHistory(SymbolEnum.BTC_USDT.getValue(), TimeEnum.FOUR_HOUR.getValue(), 2);
        log.info("{}当前价格为：{}美元", SymbolEnum.BTC_USDT.getDescription(), klineHistory.getData().get(0).getClose().setScale(2, BigDecimal.ROUND_HALF_UP));
        // 查询当前4小时K线布林轨价格
        BrinTrackVO brinTrackVO = BrinTrackUtil.getBrinTrack(SymbolEnum.BTC_USDT.getValue(), TimeEnum.FOUR_HOUR.getValue());
        log.info("当前4小时K线布林轨价格：{}", JsonUtil.toJsonString(brinTrackVO));
        // 获取最近一条已收盘4小时K线
        KlineVO klineVO = klineHistory.getData().get(1);
        if (klineHistory.getData().isEmpty()) {
            return;
        }
        // 查询数据库K线同步记录
        MarketInfo marketInfo = marketInfoRepository.findByPeriod(TimeEnum.FOUR_HOUR.getValue());
        if (marketInfo != null) {
            // K线收阳，如果趋势向上则做多
            if (klineVO.getClose().compareTo(klineVO.getOpen()) > 0 && marketInfo.getTrend().equals(TrendEnum.UPWARD.getValue())) {
                // 触发买入开多策略
                OperateUtil.getOperateHandle(OperateTypeEnum.BUY_OPEN).execute();
            }
            // K线收阴，如果趋势向下则做空
            if (klineVO.getClose().compareTo(klineVO.getOpen()) < 0 && marketInfo.getTrend().equals(TrendEnum.DOWNWARD.getValue())) {
                // 触发卖出开空策略
                OperateUtil.getOperateHandle(OperateTypeEnum.SELL_OPEN).execute();
            }
        }
    }

}
