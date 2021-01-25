package com.util;

import com.model.vo.BrinTrackVO;
import com.model.vo.KlineVO;
import com.model.vo.ResultVO;
import com.service.MarketService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description 计算布林轨价格
 * @create 2020-05-24 17:12
 */
@Component
public class BrinTrackUtil {

    private static MarketService marketService;

    public BrinTrackUtil(MarketService marketService) {
        BrinTrackUtil.marketService = marketService;
    }

    public static BrinTrackVO getBrinTrack(String symbol, String period) {
        // 查询最近20条K线数据
        ResultVO<List<KlineVO>> klineHistory = marketService.getKlineHistory(symbol, period, 20);
        // 对收盘价进行求和
        BigDecimal sum = klineHistory.getData().stream().map(KlineVO::getClose).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 计算平均值
        BigDecimal average = sum.divide(new BigDecimal(20), BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 计算方差
        sum = new BigDecimal(0);
        for (KlineVO klineVO : klineHistory.getData()) {
            BigDecimal difference = klineVO.getClose().subtract(average).abs();
            BigDecimal square = difference.multiply(difference);
            sum = sum.add(square);
        }
        Double variance = sum.divide(new BigDecimal(20), BigDecimal.ROUND_HALF_UP).doubleValue();
        // 计算标准差
        BigDecimal standardDeviation = new BigDecimal(Math.sqrt(variance));
        // 计算布林轨上轨价格
        BigDecimal upperTrack = average.add(standardDeviation.multiply(new BigDecimal(2))).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 计算布林轨下轨价格
        BigDecimal lowerTrack = average.subtract(standardDeviation.multiply(new BigDecimal(2))).setScale(2, BigDecimal.ROUND_HALF_UP);
        return BrinTrackVO.builder()
                .lowerTrack(lowerTrack)
                .middleTrack(average)
                .upperTrack(upperTrack)
                .build();
    }

}
