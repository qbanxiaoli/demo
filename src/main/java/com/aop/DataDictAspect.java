package com.aop;

import com.aop.annotation.DataDict;
import com.aop.annotation.DataDictClass;
import com.dao.repository.SysDictRepository;
import com.model.entity.SysDict;
import com.model.vo.PageVO;
import com.mysql.cj.QueryResult;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-22 16:35
 */
@Aspect
@Component
@AllArgsConstructor
public class DataDictAspect {

    private final SysDictRepository sysDictRepository;

    @Around("@annotation(dataDictClass)")
    public Object doAround(ProceedingJoinPoint point, DataDictClass dataDictClass) throws Throwable {
        // 执行，获取结果
        Object result = point.proceed();
        if (result instanceof QueryResults) {
            ((QueryResults<?>) result).getResults().forEach(this::parseDict);
        } else if (result instanceof PageVO) {
            ((PageVO<?>) result).getResults().forEach(this::parseDict);
        } else if (result instanceof List) {
            ((List<?>) result).forEach(this::parseDict);
        } else {
            this.parseDict(result);
        }
        return result;
    }

    // 数据字典转化
    @SneakyThrows
    private void parseDict(Object result) {
        Field[] fields = result.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DataDict.class)) {
                DataDict dataDict = field.getAnnotation(DataDict.class);
                Object object = FieldUtils.getFieldValue(result, dataDict.dict());
                if (object != null) {
                    SysDict sysDict = sysDictRepository.findByTableNameAndFieldNameAndFieldValue(dataDict.tableName(), dataDict.dict(), object);
                    if (sysDict != null) {
                        // 开启私有属性访问权限
                        field.setAccessible(true);
                        // 数据字典转化
                        field.set(result, sysDict.getDescription());
                    }
                }
            }
        }
    }

}
