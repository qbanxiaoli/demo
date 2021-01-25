package com.enums;

import com.strategy.handle.operate.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-13 05:32
 */
@Getter
@AllArgsConstructor
public enum OperateTypeEnum {

    DEFAULT(DefaultOperateHandle.class.getSimpleName(), "现货交易"),
    CONTRACT(ContractOperateHandle.class.getSimpleName(), "合约交易"),
    BUY_OPEN(BuyOpenOperateHandle.class.getSimpleName(), "买入开多"),
    SELL_OPEN(SellOpenOperateHandle.class.getSimpleName(), "卖出开空"),
    BUY_CLOSE(BuyCloseOperateHandle.class.getSimpleName(), "买入平空"),
    SELL_CLOSE(SellCloseOperateHandle.class.getSimpleName(), "卖出平多");

    private final String value;

    private final String description;

}
