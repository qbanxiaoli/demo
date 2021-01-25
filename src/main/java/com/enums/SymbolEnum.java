package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-12 22:13
 */
@Getter
@AllArgsConstructor
public enum SymbolEnum {

    BTC_USDT("btcusdt", "BTC/USDT交易对");

    private final String value;

    private final String description;

}
