package com.aop.annotation;

import java.lang.annotation.*;

/**
 * @author qbanxiaoli
 * @description 数据字典转换
 * @create 2020-05-22 16:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataDictClass {

}
