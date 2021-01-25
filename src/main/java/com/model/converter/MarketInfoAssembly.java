package com.model.converter;

import com.model.entity.MarketInfo;
import com.model.vo.BrinTrackVO;
import com.model.vo.KlineVO;
import org.springframework.beans.BeanUtils;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-27 18:57
 */
public class MarketInfoAssembly {

    public static MarketInfo toDomain(BrinTrackVO brinTrackVO, KlineVO klineVO) {
        MarketInfo marketInfo = new MarketInfo();
        BeanUtils.copyProperties(brinTrackVO, marketInfo);
        BeanUtils.copyProperties(klineVO, marketInfo);
        marketInfo.setId(null);
        marketInfo.setKlineId(klineVO.getId());
        return marketInfo;
    }

    public static MarketInfo toDomain(MarketInfo marketInfo, BrinTrackVO brinTrackVO, KlineVO klineVO) {
        Long id = marketInfo.getId();
        BeanUtils.copyProperties(brinTrackVO, marketInfo);
        BeanUtils.copyProperties(klineVO, marketInfo);
        marketInfo.setId(id);
        marketInfo.setKlineId(klineVO.getId());
        return marketInfo;
    }

}
