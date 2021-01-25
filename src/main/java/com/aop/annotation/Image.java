package com.aop.annotation;

import java.lang.annotation.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-24 02:19
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Image {

    String value() default "";

}
