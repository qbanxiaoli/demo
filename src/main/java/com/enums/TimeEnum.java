package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-12 21:47
 */
@Getter
@AllArgsConstructor
public enum TimeEnum {

    ONE_MINUTE("1min"),
    FIVE_MINUTE("5min"),
    FIFTEEN_MINUTE("15min"),
    SIXTY_MINUTE("60min"),
    FOUR_HOUR("4hour"),
    ONE_DAY("1day"),
    ONE_MON("1mon"),
    ONE_WEEK("1week"),
    ONE_YEAR("1year");

    private final String value;

}
