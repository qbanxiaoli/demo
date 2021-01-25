package com.model.converter;

import com.alibaba.fastjson.JSON;
import com.model.entity.SysErrorLog;
import com.model.entity.SysOperateLog;
import com.util.I18nUtil;
import com.util.IpUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-16 20:09
 */
public class SysLogAssembly {

    public static SysOperateLog toDomain(HttpServletRequest request, Object returnValue, ProceedingJoinPoint point, ApiOperation apiOperation, long startTime) {
        SysOperateLog sysOperateLog = new SysOperateLog();
        // 请求IP
        sysOperateLog.setIpAddress(IpUtil.getIpAddress());
        // 请求方式
        sysOperateLog.setRequestMethod(request.getMethod());
        // 请求路径
        sysOperateLog.setRequestUrl(request.getRequestURL().toString());
        // 接口名称
        sysOperateLog.setInterfaceName(apiOperation.value());
        // 处理方法
        sysOperateLog.setMethodName(point.toString());
        // 请求参数
        sysOperateLog.setRequestBody(Arrays.toString(point.getArgs()));
        // 返回结果
        sysOperateLog.setResponseBody(JSON.toJSONString(returnValue));
        // 请求耗时
        sysOperateLog.setRequestTime(System.currentTimeMillis() - startTime);
        return sysOperateLog;
    }

    public static SysErrorLog toDomain(JoinPoint point, ApiOperation apiOperation, Exception e) {
        SysErrorLog sysErrorLog = new SysErrorLog();
        // 接口名称
        sysErrorLog.setInterfaceName(apiOperation.value());
        // 异常类名
        sysErrorLog.setClassName(point.getTarget().getClass().getName());
        // 异常方法
        sysErrorLog.setMethodName(point.getSignature().getName());
        // 请求参数
        sysErrorLog.setRequestBody(Arrays.toString(point.getArgs()));
        // 错误信息
        sysErrorLog.setMessage(I18nUtil.getMessage(e.getMessage()));
        // 堆栈错误
        sysErrorLog.setError(e.toString());
        return sysErrorLog;
    }

}
