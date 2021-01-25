package com.aop;

import com.dao.repository.SysErrorLogRepository;
import com.dao.repository.SysOperateLogRepository;
import com.model.converter.SysLogAssembly;
import com.model.entity.SysErrorLog;
import com.model.entity.SysOperateLog;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-16 03:22
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class LogAspect {

    private final HttpServletRequest request;

    private final SysOperateLogRepository sysOperateLogRepository;

    private final SysErrorLogRepository sysErrorLogRepository;

    // 记录操作日志
    @Around("@annotation(apiOperation)")
    public Object doAround(ProceedingJoinPoint point, ApiOperation apiOperation) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 执行，获取结果
        Object result = point.proceed(point.getArgs());
        // 操作日志入库
        SysOperateLog sysOperateLog = SysLogAssembly.toDomain(request, result, point, apiOperation, startTime);
        sysOperateLogRepository.save(sysOperateLog);
        return result;
    }

    // 记录错误日志
    @AfterThrowing(value = "@annotation(apiOperation)", throwing = "e")
    public void handleThrowing(JoinPoint point, ApiOperation apiOperation, Exception e) {
        e.printStackTrace();
        // 异常日志入库
        SysErrorLog sysErrorLog = SysLogAssembly.toDomain(point, apiOperation, e);
        sysErrorLogRepository.save(sysErrorLog);
    }

}