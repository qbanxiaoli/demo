package com.strategy.handle.operate;

import com.strategy.OperateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author qbanxiaoli
 * @description 现货交易策略
 * @create 2019-11-13 05:03
 */
@Slf4j
@Service
public class DefaultOperateHandle implements OperateHandle {

    @Override
    public void execute() {
        log.info("现货交易");
    }

}
