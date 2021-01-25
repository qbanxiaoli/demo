package com.interceptor;

import com.aop.annotation.Security;
import com.querydsl.core.util.ReflectionUtils;
import com.strategy.SecretHandle;
import com.strategy.util.SecretUtil;
import com.util.JwtUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

/**
 * @author qbanxiaoli
 * @description 时间、操作人填充、字段属性加密拦截
 * @create 2020-03-25 16:22
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisUpdateInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取 SQL
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取反射对象
        Object object = invocation.getArgs()[1];
        // 获取所有属性，包括父类属性
        Set<Field> declaredFields = ReflectionUtils.getFields(object.getClass());
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            for (Field field : declaredFields) {
                if (field.getAnnotation(CreatedDate.class) != null || field.getAnnotation(LastModifiedDate.class) != null) {
                    // 开启私有属性访问权限
                    field.setAccessible(true);
                    // 填充时间
                    field.set(object, new Date());
                }
                if (field.getAnnotation(CreatedBy.class) != null || field.getAnnotation(LastModifiedBy.class) != null) {
                    // 开启私有属性访问权限
                    field.setAccessible(true);
                    // 填充操作人
                    field.set(object, JwtUtil.getUsername());
                }
                this.encrypt(object, field);
            }
        }
        if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
            for (Field field : declaredFields) {
                if (field.getAnnotation(LastModifiedDate.class) != null) {
                    // 开启私有属性访问权限
                    field.setAccessible(true);
                    // 填充时间
                    field.set(object, new Date());
                }
                if (field.getAnnotation(LastModifiedBy.class) != null) {
                    // 开启私有属性访问权限
                    field.setAccessible(true);
                    // 填充操作人
                    field.set(object, JwtUtil.getUsername());
                }
                this.encrypt(object, field);
            }
        }
        return invocation.proceed();
    }

    // 属性加密
    private void encrypt(Object object, Field field) {
        if (field.getAnnotation(Security.class) != null) {
            SecretHandle secretHandle = SecretUtil.getSecretHandle(field.getAnnotation(Security.class).type());
            secretHandle.encrypt(object, field);
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
