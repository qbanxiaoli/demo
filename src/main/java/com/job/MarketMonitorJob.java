package com.job;

import com.enums.SymbolEnum;
import com.enums.TimeEnum;
import com.model.vo.KlineVO;
import com.model.vo.ResultVO;
import com.service.MarketService;
import com.util.MailUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description 监控行情剧烈波动
 * @create 2020-06-10 09:09
 */
@Component
@AllArgsConstructor
public class MarketMonitorJob {

    private final MarketService marketService;

    private final MailProperties mailProperties;

    @Scheduled(cron = "0 * * * * ?")
    public void execute() {
        // 查询最近两条1分钟K线
        ResultVO<List<KlineVO>> klineHistory = marketService.getKlineHistory(SymbolEnum.BTC_USDT.getValue(), TimeEnum.ONE_MINUTE.getValue(), 2);
        // 获取最近一条已收盘K线
        KlineVO klineVO = klineHistory.getData().get(1);
        if (klineHistory.getData().isEmpty()) {
            return;
        }
        // 计算BTC一分钟内的波动情况
        BigDecimal total = klineHistory.getData().get(0).getClose().subtract(klineVO.getClose());
        if (total.abs().divide(klineVO.getClose(), BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal("0.005")) >= 0) {
            if (total.compareTo(BigDecimal.ZERO) > 0) {
                MailUtil.sendMail("开始拉盘", mailProperties.getUsername(), "BTC当前价格为" + klineVO.getClose() + "," +
                        "BTC1分钟前价格为" + klineHistory.getData().get(0).getClose() + "，上涨了" + total + "美元");
            } else {
                MailUtil.sendMail("开始砸盘", mailProperties.getUsername(), "BTC当前价格为" + klineVO.getClose() + "," +
                        "BTC1分钟前价格为" + klineHistory.getData().get(0).getClose() + "，下跌了" + total.abs() + "美元");
            }
        }
    }

}
