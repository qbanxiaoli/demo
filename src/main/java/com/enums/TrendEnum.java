package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-27 16:52
 */
@Getter
@AllArgsConstructor
public enum TrendEnum {

    UN_KNOW(0, "趋势未知"),
    UPWARD(1, "超跌反弹，上升趋势"),
    DOWNWARD(2, "触顶回落，下降趋势");

    private final Integer value;

    private final String description;

}
