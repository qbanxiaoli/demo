package com.strategy.handle.operate;

import com.strategy.OperateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author qbanxiaoli
 * @description 卖出平多操作策略
 * @create 2019-11-13 06:25
 */
@Slf4j
@Service
public class SellCloseOperateHandle implements OperateHandle {

    @Override
    public void execute() {
        log.info("卖出平多");
    }

}
