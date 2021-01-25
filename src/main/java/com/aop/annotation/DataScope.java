package com.aop.annotation;

import java.lang.annotation.*;

/**
 * @author qbanxiaoli
 * @description 数据权限过滤
 * @create 2020-05-24 02:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 用户表的别名
     */
    String userAlias() default "";

    /**
     * 部门表的别名
     */
    String deptAlias() default "";

    /**
     * 动态表的别名
     */
    String variable() default "";

    /**
     * 查询类型
     */
    TypeEnum type() default TypeEnum.JPA;

    enum TypeEnum {
        JPA,
        SQL,
    }

}
