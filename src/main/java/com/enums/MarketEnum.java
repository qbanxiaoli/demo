package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-11 01:50
 */
@Getter
@AllArgsConstructor
public enum MarketEnum {

    SYMBOL("symbol", "交易对"),
    PERIOD("period", "返回数据时间粒度，也就是每根蜡烛的时间区间"),
    SIZE("size", "返回K线数据条数[1,2000]"),
    DEPTH("depth", "返回深度的数量"),
    TYPE("type", "深度的价格聚合度");

    private final String value;

    private final String description;

}
