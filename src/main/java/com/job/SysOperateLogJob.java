package com.job;

import com.dao.repository.SysOperateLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author qbanxiaoli
 * @description 清空操作日志
 * @create 2020-05-14 15:46
 */
@Component
@AllArgsConstructor
public class SysOperateLogJob {

    private final SysOperateLogRepository sysOperateLogRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void execute() {
        // 每天凌晨定时清除系统操作日志，只保留当天的
        sysOperateLogRepository.deleteAllInBatch();
    }

}
