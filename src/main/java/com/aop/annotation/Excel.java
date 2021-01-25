package com.aop.annotation;

import lombok.Getter;

import java.lang.annotation.*;

/**
 * @author qbanxiaoli
 * @description 文件导入导出
 * @create 2020-05-24 02:19
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {

    String value() default "";

    TypeEnum type() default TypeEnum.ALL;

    enum TypeEnum {
        ALL,
        IMPORT,
        EXPORT
    }

}
