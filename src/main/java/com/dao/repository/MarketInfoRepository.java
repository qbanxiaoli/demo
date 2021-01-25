package com.dao.repository;

import com.model.entity.MarketInfo;
import com.dao.BaseRepository;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-27 10:53
 */
public interface MarketInfoRepository extends BaseRepository<MarketInfo, Long> {

    MarketInfo findByPeriod(String value);

}
