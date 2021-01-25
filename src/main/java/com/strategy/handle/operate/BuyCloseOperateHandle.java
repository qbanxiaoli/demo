package com.strategy.handle.operate;

import com.strategy.OperateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author qbanxiaoli
 * @description 买入平空操作策略
 * @create 2019-11-13 06:24
 */
@Slf4j
@Service
public class BuyCloseOperateHandle implements OperateHandle {

    @Override
    public void execute() {
        log.info("买入平空");
    }

}
