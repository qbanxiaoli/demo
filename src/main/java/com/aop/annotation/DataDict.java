package com.aop.annotation;

import java.lang.annotation.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-22 16:31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataDict {

    /**
     * 表名
     */
    String tableName() default "";

    /**
     * 字典字段名
     */
    String dict() default "";

}
