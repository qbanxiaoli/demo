package com.strategy.handle.operate;

import com.strategy.OperateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author qbanxiaoli
 * @description 买入开多操作策略
 * @create 2019-11-13 06:23
 */
@Slf4j
@Service
public class BuyOpenOperateHandle implements OperateHandle {

    @Override
    public void execute() {
        log.info("买入开多");
    }

}
