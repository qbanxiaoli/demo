package com.strategy.handle.operate;

import com.strategy.OperateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author qbanxiaoli
 * @description 合约交易策略
 * @create 2019-11-13 06:22
 */
@Slf4j
@Service
public class ContractOperateHandle implements OperateHandle {

    @Override
    public void execute() {
        log.info("合约交易");
    }

}
