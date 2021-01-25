package com.interceptor;

import com.aop.annotation.Security;
import com.querydsl.core.util.ReflectionUtils;
import com.strategy.SecretHandle;
import com.strategy.util.SecretUtil;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

/**
 * @author qbanxiaoli
 * @description 字段属性解密拦截
 * @create 2020-03-25 16:22
 */
@Component
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class)})
public class MybatisResultInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取反射对象
        Object result = invocation.proceed();
        ((List<?>) result).forEach(this::decrypt);
        return result;
    }

    // 属性解密
    private void decrypt(Object object) {
        // 获取所有属性，包括父类属性
        Set<Field> declaredFields = ReflectionUtils.getFields(object.getClass());
        for (Field field : declaredFields) {
            if (field.getAnnotation(Security.class) != null) {
                SecretHandle secretHandle = SecretUtil.getSecretHandle(field.getAnnotation(Security.class).type());
                secretHandle.decrypt(object, field);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
